package com.englizya.repository.impl

import com.englizya.api.ReservationService
import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest
import com.englizya.model.request.ReservationConfirmationRequest
import com.englizya.model.request.ReservationRequest
import com.englizya.model.response.OnlineTicket
import com.englizya.model.response.ReservationOrder
import com.englizya.repository.ReservationRepository

class ReservationRepositoryImpl constructor(
    private val reservationService: ReservationService,
) : ReservationRepository {

    override suspend fun reserveSeats(
        paymentRequest: PaymentRequest,
        token: String,
    ): Result<List<ReservationTicket>> = kotlin.runCatching {
        reservationService.bookSeats(paymentRequest, token)
    }

    override suspend fun requestReservation(
        reservationRequest: ReservationRequest,
        token: String
    ): Result<ReservationOrder> {
        return kotlin.runCatching {
            reservationService.requestReservation(reservationRequest, token)
        }
    }

    override suspend fun confirmReservation(
        request: ReservationConfirmationRequest,
        token: String
    ): Result<List<OnlineTicket>> = kotlin.runCatching {
        reservationService
            .confirmReservation(request, token)
    }


}