package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TripSearchRequest(
    var date: String,
    var sourceStationId: Int,
    var destinationStationId: Int
)