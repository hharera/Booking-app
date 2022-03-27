package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Trip (
    var tripId: Int,
    var tripName: String,
    var startTime: String,
    var endTime: String,
    var startDate: String,
    var endDate: String,
    var tripDays: String,
    var setNumber: String,
    var driverId: String,
    var service: Service,
    var tripTimes: TripTimes,
    var reserveType: ReserveType,
    var tripStatus: TripStatus,
    var serviceDegree: ServiceDegree,
    var lineCode: LineCode,
    var linePathStations: Set<LinePath>,
)