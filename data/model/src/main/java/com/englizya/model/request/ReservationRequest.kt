package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ReservationRequest(
    var tripId: Int,
    var pathType: Int,
    var reservationId: Int,
    var destinationBranchId: Int,
    var sourceBranchId: Int,
    var seats: Set<Int> = HashSet()
)
