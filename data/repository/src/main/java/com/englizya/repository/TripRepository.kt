package com.englizya.repository

import com.englizya.model.model.Trip
import com.englizya.model.request.TripSearchRequest

interface TripRepository {
    suspend fun getAllTrips(): Result<List<Trip>>
    suspend fun searchTrips(request: TripSearchRequest,forceOnline : Boolean = false ): Result<List<Trip>>
}