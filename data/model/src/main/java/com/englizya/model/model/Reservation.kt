package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    var id: Int,
    var tripId: Int,
    var lineId: Int,
    var destination: Int,
    var source: Int,
    var date: String,
    val seats: List<Seat>
)
