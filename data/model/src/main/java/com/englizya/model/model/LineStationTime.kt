package com.englizya.model.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class LineStationTime {
    @SerialName("lineNo")
    var lineNo: Int? = null
    @SerialName("tripId")
    var tripId: Int? = null
    @SerialName("areaId")
    var areaId: Int? = null
    @SerialName("stationType")
    var stationType: Int? = null
    @SerialName("startTime")
    var startTime: String? = null
    @SerialName("bookingOffice")
    var bookingOffice: BookingOffice? = BookingOffice()
}