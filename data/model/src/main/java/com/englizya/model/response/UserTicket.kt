package com.englizya.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Ticket")
data class UserTicket(
    var tripName: String,
    @PrimaryKey   val ticketId: Int,
    val ticketQr: String,
    var ticketingTime: String?,
    var seatNo: Int,
    var source: String,
    var sourceTime: String?,
    var destination: String,
    var destinationTime: String?,
    var serviceType: String,
    var uid: String,
    val bookingOfficeMovingTime: String?,
    val bookingOfficeRidingTime: String?,
    val bookingOfficeName: String,
    val reservationDate: String,
    val tripId: Int,
    val isActive: Boolean,
)

