package com.englizya.repository.impl

import com.englizya.api.ReservationService
import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest
import com.englizya.repository.ReservationRepository
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationService: ReservationService,
) : ReservationRepository {

    override suspend fun reserveSeats(
        paymentRequest: PaymentRequest,
        token: String,
    ): Result<List<ReservationTicket>> = kotlin.runCatching {
        reservationService.bookSeats(paymentRequest, token)
    }
}