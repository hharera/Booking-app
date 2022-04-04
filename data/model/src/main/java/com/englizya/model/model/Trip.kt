package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val tripId: Int,
    val tripName: String,
    val startTime: String,
    val endTime: String,
    val startDate: String,
    val endDate: String,
    val tripDays: String,
    val setNumber: String,
    val driverId: String,
    val service: Service,
    val tripTimes: TripTimes,
    val reserveType: ReserveType,
    val tripStatus: TripStatus,
    val serviceDegree: ServiceDegree,
    val line: Line,
    val reservation: List<Reservation>,
    val stations: List<Branch>,
)