package com.example.githubclient.feature_app.di

import com.example.githubclient.core.utils.KtorClientProvider
import com.example.githubclient.feature_app.data.local.RoomDB
import com.example.githubclient.feature_app.data.remote.api.GithubRepositoryApi
import com.example.githubclient.feature_app.data.remote.api.IGithubRepositoryApi
import com.example.githubclient.feature_app.data.repository.DownloadRepositoryImpl
import com.example.githubclient.feature_app.data.repository.SearchRepositoryImpl
import com.example.githubclient.feature_app.domain.use_case.DownloadRepositoryUseCase
import com.example.githubclient.feature_app.domain.repository.DownloadRepository
import com.example.githubclient.feature_app.domain.repository.SearchRepository
import com.example.githubclient.feature_app.domain.use_case.LocalDownloadUseCase
import com.example.githubclient.feature_app.domain.use_case.SearchHistoryUseCase
import com.example.githubclient.feature_app.domain.use_case.SearchUseCase
import com.example.githubclient.feature_app.presentation.downloads.DownloadsScreenViewModel
import com.example.githubclient.feature_app.presentation.search.SearchScreenViewModel
import org.koin.android.ext.koin.androidContext
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
        viewModelsModule,
        databaseModel
    )
}

val ktorModule: Module = module {
    single { KtorClientProvider.provideKtorClient() }
}
val useCasesModule: Module = module {
    factory { SearchUseCase(get()) }
    factory { SearchHistoryUseCase(get()) }
    factory { DownloadRepositoryUseCase(get()) }
    factory { LocalDownloadUseCase(get()) }
}
val repositoriesModule: Module = module {
    single<SearchRepository> { SearchRepositoryImpl(get()) }
    single<IGithubRepositoryApi> { GithubRepositoryApi(get()) }
    single<DownloadRepository> { DownloadRepositoryImpl(get(), androidContext(), get()) }
}

val viewModelsModule: Module = module {
    viewModel { SearchScreenViewModel(get(), get(), get(), get()) }
    viewModel { DownloadsScreenViewModel(get()) }
}

val databaseModel: Module = module {
    single { RoomDB.getInstance(androidContext()) }
    single { get<RoomDB>().getGithubRepositoryDao() }
    single { get<RoomDB>().getDownloadDao() }
}