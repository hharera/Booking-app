package com.englizya.api

import com.englizya.model.model.Trip
import com.englizya.model.request.TripSearchRequest

interface TripService {
    suspend fun getAllTrips() : List<Trip>
    suspend fun searchTrips(tripRequest : TripSearchRequest) : List<Trip>
}