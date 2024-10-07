package com.example.githubclient.feature_app.domain.use_case

import com.example.githubclient.feature_app.data.local.dao.GithubRepositoryDao
import com.example.githubclient.feature_app.domain.entity.search_history.SearchHistoryEntity

class SearchHistoryUseCase(private val dao: GithubRepositoryDao) {


    suspend fun save(entity: SearchHistoryEntity) = dao.save(entity)
    fun get(id: Int) = dao.get(id)
    fun getAll() = dao.getAll()

}