package com.englizya.model.request

import kotlinx.serialization.Serializable

/**
 * 1. to check if transaction is valid
 * 2. to check if transaction is used before
 * TODO : Suspend seats when request to reserve
 * 3. to check if the seats if available or not
 * 4. calculate the total and check if the amount paid are equal
 */

@Serializable
data class ReservationConfirmationRequest(
    var transactionRef: String,
    var tripId: Int,
    var pathType: Int,
    var reservationId: Int,
    var destinationBranchId: Int,
    var selectedBookingOffice: Int,
    var sourceBranchId: Int,
    var seats: Set<Int> = HashSet()
)
