package com.englizya.model.request

import com.englizya.model.model.Seat
import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("date") var date: String,
    @SerializedName("source") var source: Int,
    @SerializedName("destination") var destination: Int,
    @SerializedName("reservationId") var reservationId: String,
    @SerializedName("tripId") var tripId: String,
    @SerializedName("trip_name") var tripName: String,
    @SerializedName("item_price") var itemPrice: Int,
    @SerializedName("qty") var qty: Int,
    @SerializedName("phoneMobile") var phoneMobile: String,
    @SerializedName("passenger") var passenger: String,
    @SerializedName("seats") var seats: List<Seat> = arrayListOf()
)