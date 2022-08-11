package com.englizya.api.impl

import com.englizya.api.TripService
import com.englizya.api.utils.Routing
import com.englizya.model.model.Trip
import com.englizya.model.request.TripSearchRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class TripServiceImpl constructor(
    private val client: HttpClient
) : TripService {

    override suspend fun getAllTrips(): List<Trip> =
        client.get {
            url(Routing.GET_ALL_TRIPS)
            contentType(ContentType.Application.Json)
        }.body()

    @OptIn(InternalAPI::class)
    override suspend fun searchTrips(tripRequest: TripSearchRequest): List<Trip> =
        client.post(Routing.SEARCH_TRIPS) {
            setBody(tripRequest)
            contentType(ContentType.Application.Json)
            timeout {
                requestTimeoutMillis = 30000
            }
        }.body()
}