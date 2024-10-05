package com.example.githubclient.feature_app.data.remote.api

import com.example.githubclient.feature_app.data.remote.response.search_repositories.GithubSearchRepositoryResponseModel

interface IGithubRepositoryApi {
    suspend fun searchRepositories(q: String): GithubSearchRepositoryResponseModel
}