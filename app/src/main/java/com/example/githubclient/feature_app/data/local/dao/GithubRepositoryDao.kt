package com.example.githubclient.feature_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubclient.feature_app.domain.entity.search_history.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: SearchHistoryEntity)

    @Query("select * from github_repositories where `id` = :id")
    fun get(id: Int): Flow<SearchHistoryEntity?>

    @Query("select * from github_repositories")
    fun getAll(): Flow<List<SearchHistoryEntity>>
}