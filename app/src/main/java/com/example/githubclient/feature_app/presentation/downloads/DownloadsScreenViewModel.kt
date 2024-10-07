package com.example.githubclient.feature_app.presentation.downloads

import androidx.lifecycle.ViewModel
import com.example.githubclient.feature_app.domain.use_case.LocalDownloadUseCase

class DownloadsScreenViewModel(
    private val localDownloadUseCase: LocalDownloadUseCase
): ViewModel() {

    val allDownloads = localDownloadUseCase.getAll()


}