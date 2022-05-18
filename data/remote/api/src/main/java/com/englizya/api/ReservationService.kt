package com.englizya.api

import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest
import com.englizya.model.request.ReservationConfirmationRequest
import com.englizya.model.request.ReservationRequest
import com.englizya.model.response.OnlineTicket
import com.englizya.model.response.ReservationOrder

interface ReservationService {
    suspend fun bookSeats(
        paymentRequest: PaymentRequest,
        token: String
    ): List<ReservationTicket>

    suspend fun requestReservation(
        reservationRequest: ReservationRequest, token: String
    ): ReservationOrder

    suspend fun confirmReservation(
        request: ReservationConfirmationRequest,
        token: String
    ) : List<ReservationTicket>
}