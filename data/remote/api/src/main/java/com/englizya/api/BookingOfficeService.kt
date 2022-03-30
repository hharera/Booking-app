package com.englizya.api

import com.englizya.model.model.BookingOffice

interface BookingOfficeService {
    suspend fun getAllBookingOffices(): List<BookingOffice>
}