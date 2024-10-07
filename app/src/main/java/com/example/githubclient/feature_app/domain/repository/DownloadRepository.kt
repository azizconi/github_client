package com.example.githubclient.feature_app.domain.repository

import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import kotlinx.coroutines.Job
import java.io.File

interface DownloadRepository {
    suspend fun downloadRepository(
        repo: GithubRepositoryResponseModel,
        repoUrl: String,
        outputFile: File,
        onProgress: (Float) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    )

    fun pauseDownload(repoUrl: String)
    fun resumeDownload(
        repo: GithubRepositoryResponseModel,
        repoUrl: String,
        outputFile: File,
        onProgress: (Float) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    )
    fun cancelDownload(repoUrl: String)
}