package com.englizya.api.impl

import com.englizya.api.ReservationService
import com.englizya.api.utils.Routing
import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class ReservationServiceImpl @Inject constructor(
    private val client: HttpClient
) : ReservationService {

    override suspend fun bookSeats(
        paymentRequest: PaymentRequest,
        token: String
    ): List<ReservationTicket> =
        client.post(Routing.BOOK_SEATS) {
            headers.append(HttpHeaders.Authorization, token)

            contentType(ContentType.Application.Json)
            body = paymentRequest
        }
}