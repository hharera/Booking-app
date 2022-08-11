package com.englizya.api.di

import com.englizya.api.utils.Request
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import org.koin.dsl.module
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import java.net.ConnectException


val clientModule = module {

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 10)
                retryIf { request, response ->
                    !response.status.isSuccess()
                }
                retryOnExceptionIf { request, cause ->
                    cause is HttpRequestTimeoutException || cause is ConnectException
                }
                delayMillis { retry ->
                    retry * 3000L
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = Request.TIME_OUT
            }
        }
    }
}