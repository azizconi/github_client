package com.example.githubclient.core.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object KtorClientProvider {

    @OptIn(ExperimentalSerializationApi::class)
    fun provideKtorClient(): HttpClient {
        return HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
                connectTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }
            defaultRequest {
                header("User-Agent", "GitHubClient")
                header("Accept", "application/vnd.github.v3+json")
            }
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