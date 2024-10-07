package com.example.githubclient.feature_app.data.repository

import android.content.Context
import android.util.Log
import com.example.githubclient.core.utils.DownloadNotificationManager
import com.example.githubclient.feature_app.data.local.dao.DownloadDao
import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import com.example.githubclient.feature_app.domain.repository.DownloadRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.readBytes
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import java.io.File
import java.io.RandomAccessFile
import kotlinx.coroutines.*
import java.util.Date

class DownloadRepositoryImpl(
    private val client: HttpClient,
    private val context: Context,
    private val downloadDao: DownloadDao,
) : DownloadRepository {

    private val downloadJobs = mutableMapOf<String, Job>()
    private val notificationManager = DownloadNotificationManager(context)


    override suspend fun downloadRepository(
        repo: GithubRepositoryResponseModel,
        repoUrl: String,
        outputFile: File,
        onProgress: (Float) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        Log.e("TAG", "downloadRepository: start")
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    notificationManager.showProgress(repoUrl, "Загрузка ${outputFile.name}", null)
                }
                downloadDao.save(
                    repo.toDownloadEntity(
                        isDownloaded = false,
                        isLoading = true,
                        downloadedDate = Date().time
                    )
                )

                val downloadedBytes = if (outputFile.exists()) outputFile.length() else 0L

                val response: HttpResponse = client.get(repoUrl) {
                    method = HttpMethod.Head
                    if (downloadedBytes > 0) {
                        header(HttpHeaders.Range, "bytes=$downloadedBytes-")
                    }
                }

                if (response.status != HttpStatusCode.OK && response.status != HttpStatusCode.PartialContent) {
                    throw Exception("Unexpected response status: ${response.status}")
                }

                val contentLength = response.headers[HttpHeaders.ContentLength]?.toLong() ?: -1L
                val totalBytes = if (downloadedBytes > 0 && contentLength > 0) {
                    downloadedBytes + contentLength
                } else {
                    response.contentLength() ?: -1L
                }
                Log.e("TAG", "downloadRepository: totalBytes = ${(totalBytes / 8) / 1024}")


                outputFile.writeBytes(response.readBytes())


                withContext(Dispatchers.Main) {
                    notificationManager.completeNotification(repoUrl)
                    downloadDao.save(
                        repo.toDownloadEntity(
                            isDownloaded = true,
                            isLoading = false,
                            downloadedDate = Date().time
                        )
                    )
                    onComplete()
                }
            } catch (e: CancellationException) {
                Log.e("TAG", "downloadRepository: cancel error: ${e.message}")
                downloadDao.delete(repo.id)

                withContext(Dispatchers.Main) {
                    notificationManager.cancelNotification(repoUrl)
                }
            } catch (e: Exception) {
                Log.e("TAG", "downloadRepository: error: ${e.message}")
                downloadDao.delete(repo.id)

                withContext(Dispatchers.Main) {
                    notificationManager.cancelNotification(repoUrl)
                    onError(e)
                }
            }
        }

        downloadJobs[repoUrl] = job
    }

    override fun pauseDownload(repoUrl: String) {
        downloadJobs[repoUrl]?.cancel()
    }

    override fun resumeDownload(
        repo: GithubRepositoryResponseModel,
        repoUrl: String,
        outputFile: File,
        onProgress: (Float) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            downloadRepository(repo = repo, repoUrl, outputFile, onProgress, onComplete, onError)
        }
    }

    override fun cancelDownload(repoUrl: String) {
        downloadJobs[repoUrl]?.cancel()
        downloadJobs.remove(repoUrl)
        // Опционально: удалить файл
    }
}