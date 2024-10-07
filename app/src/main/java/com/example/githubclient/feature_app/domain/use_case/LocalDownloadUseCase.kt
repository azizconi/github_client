package com.example.githubclient.feature_app.domain.use_case

import com.example.githubclient.feature_app.data.local.dao.DownloadDao
import com.example.githubclient.feature_app.domain.entity.download.DownloadEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LocalDownloadUseCase(private val dao: DownloadDao) {

    suspend fun save(entity: DownloadEntity) = dao.save(entity)

    fun getAll() = dao.getAll()

    fun get(id: Int): Flow<DownloadEntity?> = dao.get(id)

    suspend fun delete(id: Int) = dao.delete(id)

    suspend fun cleanTableOnAppCreate() {
        getAll().first().forEach {
            if (it.isLoading) delete(it.id)
        }
    }
}