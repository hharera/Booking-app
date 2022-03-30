package com.englizya.repository

import com.englizya.model.model.BookingOffice

interface BookingOfficeRepository {
    suspend fun getAllBookingOffices(): Result<List<BookingOffice>>
}