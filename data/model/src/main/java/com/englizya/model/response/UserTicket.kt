package com.englizya.model.response

import com.englizya.model.model.TripTimes
import kotlinx.serialization.Serializable

@Serializable
data class UserTicket(
    var tripName: String,
    val ticketId: Int,
    val ticketQr: String,
    var ticketingTime: String,
    var seatNo: Int,
    var source: String,
    var sourceTime: String?,
    var destination: String,
    var destinationTime: String?,
    var sourceOfficeList: List<TripTimes>,
    var serviceType: String?,
    var uid: String,
)
