package com.example.githubclient.feature_app.domain.use_case

import com.example.githubclient.core.utils.Constants
import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import com.example.githubclient.feature_app.domain.repository.DownloadRepository
import java.io.File

class DownloadRepositoryUseCase(
    private val downloadRepository: DownloadRepository,
) {
    suspend fun execute(
        repo: GithubRepositoryResponseModel,
        outputFile: File,
        onProgress: (Float) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        downloadRepository.downloadRepository(
            repo = repo,
            (Constants.BASE_URL + "repos/${repo.owner?.login}/${repo.name}/zipball/"),
            outputFile,
            onProgress,
            onComplete,
            onError
        )
    }

    fun pause(repoUrl: String) {
        downloadRepository.pauseDownload(repoUrl)
    }

    fun resume(
        repo: GithubRepositoryResponseModel,
        outputFile: File,
        onProgress: (Float) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        downloadRepository.resumeDownload(
            repo = repo,
            repo.url ?: return,
            outputFile,
            onProgress,
            onComplete,
            onError
        )
    }

    fun cancel(repoUrl: String) {
        downloadRepository.cancelDownload(repoUrl)
    }
}
