package com.englyzia.booking.utils

sealed class BookingType {
    object OneWayBooking : BookingType()
    object RoundBooking : BookingType()
}