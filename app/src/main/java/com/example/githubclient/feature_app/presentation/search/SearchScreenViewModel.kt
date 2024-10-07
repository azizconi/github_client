package com.example.githubclient.feature_app.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.core.utils.Resource
import com.example.githubclient.core.utils.asState
import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import com.example.githubclient.feature_app.data.remote.response.search_repositories.GithubSearchRepositoryResponseModel
import com.example.githubclient.feature_app.domain.use_case.DownloadRepositoryUseCase
import com.example.githubclient.feature_app.domain.entity.search_history.SearchHistoryEntity
import com.example.githubclient.feature_app.domain.state.DownloadState
import com.example.githubclient.feature_app.domain.use_case.LocalDownloadUseCase
import com.example.githubclient.feature_app.domain.use_case.SearchHistoryUseCase
import com.example.githubclient.feature_app.domain.use_case.SearchUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class SearchScreenViewModel(
    private val searchUseCase: SearchUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val downloadUseCase: DownloadRepositoryUseCase,
    private val localDownloadUseCase: LocalDownloadUseCase
) : ViewModel() {

    private val _searchResult =
        mutableStateOf<Resource<GithubSearchRepositoryResponseModel>>(Resource.Idle)
    val searchResult = _searchResult.asState()

    private var typingJob: Job? = null

    val allSearchesHistory = searchHistoryUseCase.getAll()
    val allDownloadingRepositories = localDownloadUseCase.getAll()

    fun searchRepositories(q: String) {
        typingJob?.cancel()
        typingJob = viewModelScope.launch {
            delay(1000)
            searchUseCase(q).collect {
                _searchResult.value = it
            }
        }
    }

    fun saveSearchHistory(entity: SearchHistoryEntity) {
        viewModelScope.launch {
            searchHistoryUseCase.save(entity)
        }
    }

    fun getSearchHistory(id: Int) = searchHistoryUseCase.get(id)


    fun cancelSearching() {
        typingJob?.cancel()
    }





    private val _downloadStates = MutableStateFlow<Map<String, DownloadState>>(emptyMap())
    val downloadStates: StateFlow<Map<String, DownloadState>> = _downloadStates

    private val downloadJobs = mutableMapOf<String, Job>()

    fun startDownload(repo: GithubRepositoryResponseModel, outputFile: File) {
        val repoUrl = repo.url
        val initialState = DownloadState(
            repoUrl = repoUrl ?: return,
            isDownloading = true
        )
        _downloadStates.update {
            it + (repoUrl to initialState)
        }

        val job = viewModelScope.launch {
            downloadUseCase.execute(
                repo,
                outputFile,
                onProgress = { progress ->
                    _downloadStates.update { states ->
                        val currentState = states[repoUrl] ?: initialState
                        states + (repoUrl to currentState.copy(progress = progress))
                    }
                },
                onComplete = {
                    _downloadStates.update { states ->
                        val currentState = states[repoUrl] ?: initialState
                       states + (repoUrl to currentState.copy(isDownloading = false, isCompleted = true))
                    }
                },
                onError = { error ->
                    _downloadStates.update { states ->
                        val currentState = states[repoUrl] ?: initialState
                        states + (repoUrl to currentState.copy(isDownloading = false, error = error))
                    }
                }
            )
        }

        downloadJobs[repoUrl] = job
    }

    fun pauseDownload(repoUrl: String) {
        downloadUseCase.pause(repoUrl)
        downloadJobs[repoUrl]?.cancel()
        _downloadStates.update { states ->
            val currentState = states[repoUrl]
            if (currentState != null) {
                states + (repoUrl to currentState.copy(isDownloading = false, isPaused = true))
            } else {
                states
            }
        }
    }

    fun resumeDownload(repo: GithubRepositoryResponseModel, outputFile: File) {
        val repoUrl = repo.url
        val currentState = _downloadStates.value[repoUrl]
        if (currentState != null && currentState.isPaused) {
            _downloadStates.update { states ->
                states + ((repoUrl ?: return) to currentState.copy(isDownloading = true, isPaused = false))
            }

            val job = viewModelScope.launch {
                downloadUseCase.resume(
                    repo,
                    outputFile,
                    onProgress = { progress ->
                        _downloadStates.update { states ->
                            val currentState = states[repoUrl] ?: DownloadState(repoUrl = repoUrl ?: return@resume)
                            states + ((repoUrl ?: return@resume) to currentState.copy(progress = progress))
                        }
                    },
                    onComplete = {
                        _downloadStates.update { states ->
                            val currentState = states[repoUrl] ?: DownloadState(repoUrl = (repoUrl ?: return@resume))
                            states + ((repoUrl ?: return@resume) to currentState.copy(isDownloading = false, isCompleted = true))
                        }
                    },
                    onError = { error ->
                        _downloadStates.update { states ->
                            val currentState = states[repoUrl] ?: DownloadState(repoUrl = (repoUrl ?: return@resume))
                            states + ((repoUrl ?: return@resume) to currentState.copy(isDownloading = false, error = error))
                        }
                    }
                )
            }

            downloadJobs[repoUrl ?: return] = job
        }
    }

    fun cancelDownload(repoUrl: String) {
        downloadUseCase.cancel(repoUrl)
        downloadJobs[repoUrl]?.cancel()
        downloadJobs.remove(repoUrl)
        _downloadStates.update { states ->
            states - repoUrl
        }
    }



}