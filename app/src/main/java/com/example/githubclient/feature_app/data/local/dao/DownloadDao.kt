package com.example.githubclient.feature_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubclient.feature_app.domain.entity.download.DownloadEntity
import com.example.githubclient.feature_app.domain.entity.search_history.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: DownloadEntity)

    @Query("select * from download_repositories where `id` = :id")
    fun get(id: Int): Flow<DownloadEntity?>

    @Update
    suspend fun update(entity: DownloadEntity)

    @Query("select * from download_repositories")
    fun getAll(): Flow<List<DownloadEntity>>

    @Query("delete from download_repositories where `id` = :id")
    suspend fun delete(id: Int)
}