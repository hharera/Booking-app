package com.englizya.model.request

import com.englizya.model.model.Seat
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    var date: String,
    var source: Int,
    var destination: Int,
    var reservationId: String,
    var tripId: String,
    var tripName: String,
    var itemPrice: Int,
    var qty: Int,
    var phoneMobile: String,
    var passenger: String,
    var seats: List<Seat> = arrayListOf(),
)