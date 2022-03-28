package com.englizya.api

import com.englizya.model.model.Trip

interface TripService {
    suspend fun getAllTrips() : List<Trip>
}