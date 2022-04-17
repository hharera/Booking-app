package com.englizya.repository.impl

import com.englizya.api.BookingOfficeService
import com.englizya.model.model.BookingOffice
import com.englizya.repository.BookingOfficeRepository

class BookingOfficeRepositoryImpl constructor(
    private val bookingOfficeService: BookingOfficeService
) : BookingOfficeRepository {

    override suspend fun getAllBookingOffices(): Result<List<BookingOffice>> = kotlin.runCatching {
        bookingOfficeService.getAllBookingOffices()
    }
}