package com.englizya.model.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class Trip(
    var tripId: Int,
    var tripName: String?,
    var startTime: String?,
    var endTime: String?,
    var startDate: String?,
    var endDate: String?,
    var tripDays: String?,
    var setNumber: String?,
    var driverId: String?,
    var service: Service?,
    var tripTimes: List<LineStationTime>?,
    var reserveType: ReserveType?,
    var tripStatus: TripStatus?,
    var serviceDegree: ServiceDegree?,
    var line: Line,
    var reservation: List<Reservation>?,
    var plan: LinePricePlanMaster? = null,
//    var bus: Bus? = null,
)

data class ExampleJson2KtKotlin(

    @SerializedName("tripId") var tripId: Int? = null,
    @SerializedName("tripName") var tripName: String? = null,
    @SerializedName("startTime") var startTime: String? = null,
    @SerializedName("endTime") var endTime: String? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("tripDays") var tripDays: String? = null,
    @SerializedName("setNumber") var setNumber: String? = null,
    @SerializedName("driverId") var driverId: String? = null,
    @SerializedName("service") var service: Service? = null,
    @SerializedName("tripTimes") var tripTimes: ArrayList<LineStationTime> = arrayListOf(),
    @SerializedName("reserveType") var reserveType: ReserveType? = ReserveType(),
    @SerializedName("tripStatus") var tripStatus: TripStatus? = TripStatus(),
    @SerializedName("serviceDegree") var serviceDegree: ServiceDegree? = ServiceDegree(),
    @SerializedName("line") var line: Line?,
    @SerializedName("reservation") var reservation: ArrayList<Reservation> = arrayListOf(),
    @SerializedName("plan") var plan: LinePricePlanMaster?

)