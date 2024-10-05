package com.example.githubclient.feature_app.data.remote.response.github_repository

import kotlinx.serialization.Serializable

@Serializable
data class License(
    val key: String?,
    val name: String?,
    val spdx_id: String?,
    val url: String?,
    val node_id: String?,
)