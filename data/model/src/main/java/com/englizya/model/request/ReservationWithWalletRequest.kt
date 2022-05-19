package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ReservationWithWalletRequest(
    var tripId: Int,
    var pathType: Int,
    var reservationId: Int,
    var sourceBranchId: Int,
    var destinationBranchId: Int,
    var selectedBookingOffice: Int,
    var seats: Set<Int>,
)
