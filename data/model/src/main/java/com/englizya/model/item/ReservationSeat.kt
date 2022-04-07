package com.englizya.model.item

import kotlinx.serialization.Serializable

@Serializable
data class ReservationSeat(
    val seatNo: Int,
    val tripName: String,
    val tripTime: String,
    val source: String,
    val destination: String,
)