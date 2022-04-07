package com.englizya.model.request

import com.englizya.model.model.Seat
import com.englizya.model.model.Station

data class PaymentRequest(
    val seats: Set<Seat>,
    val source: Station,
    val destination: Station,
    val date: String,
)