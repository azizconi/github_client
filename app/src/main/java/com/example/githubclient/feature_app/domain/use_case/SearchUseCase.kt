package com.example.githubclient.feature_app.domain.use_case

import com.example.githubclient.core.utils.asResult
import com.example.githubclient.feature_app.domain.repository.SearchRepository

class SearchUseCase(private val searchRepository: SearchRepository) {

    operator fun invoke(q: String) = searchRepository.searchGithubRepositories(q).asResult()

}