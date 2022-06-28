package com.englizya.api.impl

import com.englizya.api.RouteService
import com.englizya.api.utils.Routing
import com.englizya.model.model.LineDetails
import com.englizya.model.model.Routes
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class RouteServiceImpl constructor(
    private val client: HttpClient
) : RouteService {
    override suspend fun getExternalLines(): List<Routes> =
        client.get(Routing.GET_EXTERNAL_LINES)

    override suspend fun getInternalLines(): List<Routes> =
        client.get(Routing.GET_INTERNAL_LINES)

}