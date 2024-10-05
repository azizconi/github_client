package com.example.githubclient.feature_app.di

import com.example.githubclient.core.utils.KtorClientProvider
import com.example.githubclient.feature_app.data.remote.api.GithubRepositoryApi
import com.example.githubclient.feature_app.data.remote.api.IGithubRepositoryApi
import com.example.githubclient.feature_app.data.repository.SearchRepositoryImpl
import com.example.githubclient.feature_app.domain.repository.SearchRepository
import com.example.githubclient.feature_app.domain.use_case.SearchUseCase
import com.example.githubclient.feature_app.presentation.search.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        ktorModule,
        useCasesModule,
        repositoriesModule,
        viewModelsModule
    )
}

val ktorModule: Module = module {
    single { KtorClientProvider.provideKtorClient() }
}
val useCasesModule: Module = module {
    factory { SearchUseCase(get()) }
}
val repositoriesModule: Module = module {
    single<SearchRepository> { SearchRepositoryImpl(get()) }
    single<IGithubRepositoryApi> { GithubRepositoryApi(get()) }
}

val viewModelsModule = module {
    viewModel { SearchScreenViewModel(get()) }
}