package com.englizya.repository.impl

import com.englizya.api.TripService
import com.englizya.model.model.Trip
import com.englizya.repository.TripRepository
import javax.inject.Inject


 class TripRepositoryImpl @Inject constructor(
    private val tripService: TripService
) : TripRepository {

    override suspend fun getAllTrips(): Result<List<Trip>> = kotlin.runCatching {
        tripService.getAllTrips()
    }
}