package com.englizya.api.impl

import com.englizya.api.RouteService
import com.englizya.api.utils.Routing
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class RouteServiceImpl constructor(
    private val client: HttpClient
) : RouteService {
    override suspend fun getExternalLines(): List<ExternalRoutes> =
        client.get(Routing.GET_EXTERNAL_LINES)

    override suspend fun getInternalLines(): List<InternalRoutes> =
        client.get(Routing.GET_INTERNAL_LINES) {
            timeout {
                requestTimeoutMillis = 100000
            }
        }
}