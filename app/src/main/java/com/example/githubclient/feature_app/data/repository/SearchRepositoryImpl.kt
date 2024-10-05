package com.example.githubclient.feature_app.data.repository

import com.example.githubclient.feature_app.data.remote.api.IGithubRepositoryApi
import com.example.githubclient.feature_app.data.remote.response.search_repositories.GithubSearchRepositoryResponseModel
import com.example.githubclient.feature_app.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(private val api: IGithubRepositoryApi): SearchRepository {
    override fun searchGithubRepositories(q: String): Flow<GithubSearchRepositoryResponseModel> {
        return flow { emit(api.searchRepositories(q)) }
    }
}