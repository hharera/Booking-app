package com.englizya.repository

import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest
import com.englizya.model.request.ReservationConfirmationRequest
import com.englizya.model.request.ReservationRequest
import com.englizya.model.response.OnlineTicket
import com.englizya.model.response.ReservationOrder

interface ReservationRepository {
    suspend fun reserveSeats(
        paymentRequest: PaymentRequest,
        token: String
    ): Result<List<ReservationTicket>>

    suspend fun requestReservation(
        reservationRequest: ReservationRequest,
        token: String
    ): Result<ReservationOrder>

    suspend fun confirmReservation(
        request: ReservationConfirmationRequest,
        token: String
    ): Result<List<ReservationTicket>>
}