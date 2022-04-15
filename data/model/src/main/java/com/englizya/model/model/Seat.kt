package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Seat(
    var tripReservationId: Int? = null,
    var seatId: Int? = null,
    var source: Int? = null,
    var destination: Int? = null,
    var closeFlag: Int? = null,
    var seatStatus: String? = null,
)