package com.englizya.api.impl

import com.englizya.api.TripService
import com.englizya.api.utils.Routing
import com.englizya.model.model.Trip
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class TripServiceImpl @Inject constructor(
    private val client: HttpClient
) : TripService {

    override suspend fun getAllTrips(): List<Trip> =
        client.get {
            url(Routing.GET_ALL_TRIPS)
            contentType(ContentType.Application.Json)
        }

}