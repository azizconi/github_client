package com.example.githubclient.feature_app.data.remote.api

import android.util.Log
import com.example.githubclient.core.utils.Constants.SEARCH_REPOSITORIES_BASE_URL
import com.example.githubclient.feature_app.data.remote.response.search_repositories.GithubSearchRepositoryResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class GithubRepositoryApi(private val httpClient: HttpClient): IGithubRepositoryApi {
    override suspend fun searchRepositories(q: String): GithubSearchRepositoryResponseModel {
        val response = httpClient.get("$SEARCH_REPOSITORIES_BASE_URL?q=$q")
        return response.body()
    }
}