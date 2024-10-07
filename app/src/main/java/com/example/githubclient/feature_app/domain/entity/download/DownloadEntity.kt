package com.example.githubclient.feature_app.domain.entity.download

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubclient.feature_app.data.remote.response.github_repository.Owner

@Entity(tableName = "download_repositories")
data class DownloadEntity(
    @PrimaryKey
    val id: Int,
    val description: String?,
    val full_name: String?,
    val git_url: String?,
    val html_url: String,
    val name: String?,
    val owner: Owner?,
    val url: String?,
    val isDownloaded: Boolean,
    val isLoading: Boolean,
    val downloadedDate: Long,
)
