package com.example.githubclient.feature_app.domain.state

data class DownloadState(
    val repoUrl: String,
    val progress: Float = 0f,
    val isDownloading: Boolean = false,
    val isPaused: Boolean = false,
    val isCompleted: Boolean = false,
    val error: Throwable? = null
)