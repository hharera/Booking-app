package com.englizya.api.impl

import com.englizya.api.ReservationService
import com.englizya.api.utils.Header
import com.englizya.api.utils.Header.BEARER
import com.englizya.api.utils.Routing
import com.englizya.api.utils.Routing.REQUEST_RESERVATION
import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest
import com.englizya.model.request.ReservationConfirmationRequest
import com.englizya.model.request.ReservationRequest
import com.englizya.model.request.ReservationWithWalletRequest
import com.englizya.model.response.ReservationOrder
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class ReservationServiceImpl constructor(
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

    override suspend fun requestReservation(
        reservationRequest: ReservationRequest,
        token: String
    ): ReservationOrder =
        client.post(REQUEST_RESERVATION) {
            headers.append(HttpHeaders.Authorization, "Bearer $token")

            contentType(ContentType.Application.Json)
            body = reservationRequest
        }

    override suspend fun confirmReservation(
        request: ReservationConfirmationRequest,
        token: String
    ): List<ReservationTicket> =
        client.post(Routing.CONFIRM_RESERVATION) {
            with(headers) { append(HttpHeaders.Authorization, "Bearer $token") }
            contentType(ContentType.Application.Json)
            body = request
        }

    override suspend fun confirmReservation(
        request: ReservationWithWalletRequest,
        token: String
    ): List<ReservationTicket> =
        client.post(Routing.RESERVE_WITH_WALLET) {
            with(headers) { append(HttpHeaders.Authorization, "$BEARER $token") }
            contentType(ContentType.Application.Json)
            body = request
        }

}