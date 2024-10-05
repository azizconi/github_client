package com.example.githubclient.feature_app.domain.repository

import com.example.githubclient.feature_app.data.remote.response.search_repositories.GithubSearchRepositoryResponseModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchGithubRepositories(q: String): Flow<GithubSearchRepositoryResponseModel>

}