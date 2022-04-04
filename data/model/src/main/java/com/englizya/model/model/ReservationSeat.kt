package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class ReservationSeat(
    private val tripReservationId: Int? = null,
    private val seatId: Int? = null,
    private val source: Int? = null,
    private val destination: Int? = null
)