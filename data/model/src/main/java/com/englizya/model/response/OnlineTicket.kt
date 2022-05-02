package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OnlineTicket(
    var ticketId: Int,
    var reservationId: Int,
    var seatNo: Int,
    var source: Int,
    var destination: Int,
    var ticketingTime: String,
    var uid: String,
)