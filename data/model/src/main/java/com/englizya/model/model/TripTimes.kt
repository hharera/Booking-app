package com.englizya.model.model

import kotlinx.serialization.Serializable


@Serializable
class TripTimes {
    var tripId: Int? = null
    var areaId: Int? = null
    var startTime: Int? = null
    var bookingOfficeId: Int? = null
    var stationType: Int? = null
    var onlineService: Int? = null
    var appService: Int? = null
    var media: Int? = null
    var bookingOffice: BookingOffice? = null
}