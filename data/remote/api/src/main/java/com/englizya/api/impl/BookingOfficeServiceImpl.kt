package com.englizya.api.impl

import com.englizya.api.BookingOfficeService
import com.englizya.api.utils.Routing
import com.englizya.model.model.BookingOffice
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class BookingOfficeServiceImpl constructor(
    private val client: HttpClient
): BookingOfficeService {

    override suspend fun getAllBookingOffices(): List<BookingOffice> =
        client.get(Routing.GET_ALL_OFFICES) {
            contentType(ContentType.Application.Json)
        }.body()
}