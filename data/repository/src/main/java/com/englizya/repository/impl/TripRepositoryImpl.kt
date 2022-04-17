package com.englizya.repository.impl

import com.englizya.api.TripService
import com.englizya.model.model.Trip
import com.englizya.model.request.TripSearchRequest
import com.englizya.repository.TripRepository


class TripRepositoryImpl constructor(
    private val tripService: TripService
) : TripRepository {

    override suspend fun getAllTrips(): Result<List<Trip>> = kotlin.runCatching {
        tripService.getAllTrips()
    }

    override suspend fun searchTrips(request: TripSearchRequest): Result<List<Trip>> =
        kotlin.runCatching {
            tripService.searchTrips(request)
        }
}