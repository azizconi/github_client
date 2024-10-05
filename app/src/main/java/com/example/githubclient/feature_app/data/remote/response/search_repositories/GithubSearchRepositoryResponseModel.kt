package com.example.githubclient.feature_app.data.remote.response.search_repositories

import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchRepositoryResponseModel(
    val incomplete_results: Boolean,
    val items: List<GithubRepositoryResponseModel>,
    val total_count: Int
)