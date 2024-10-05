package com.example.githubclient.feature_app.presentation.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.core.utils.Resource
import com.example.githubclient.core.utils.asState
import com.example.githubclient.feature_app.data.remote.response.search_repositories.GithubSearchRepositoryResponseModel
import com.example.githubclient.feature_app.domain.use_case.SearchUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _searchResult =
        mutableStateOf<Resource<GithubSearchRepositoryResponseModel>>(Resource.Idle)
    val searchResult = _searchResult.asState()

    private val isTypingEnded = mutableStateOf(false)
    private var typingJob: Job? = null

    fun searchRepositories(q: String) {
        Log.e("TAG", "searchRepositories: ", )
        typingJob?.cancel()
        typingJob = viewModelScope.launch {
            delay(1000)
            searchUseCase(q).collect {
                _searchResult.value = it
            }
        }
    }

    fun cancelSearching() {
        typingJob?.cancel()
    }


}