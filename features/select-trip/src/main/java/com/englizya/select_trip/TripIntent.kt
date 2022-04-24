package com.englizya.select_trip

sealed class TripIntent() {
    class SelectTrip(val tripId: Int) : TripIntent()
    class SelectBookingOffice(val bookingOfficeId: Int) : TripIntent()
}
