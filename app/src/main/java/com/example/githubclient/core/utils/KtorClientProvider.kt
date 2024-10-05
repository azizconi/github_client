package com.example.githubclient.core.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object KtorClientProvider {

    @OptIn(ExperimentalSerializationApi::class)
    fun provideKtorClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.BODY
            }
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = true
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}