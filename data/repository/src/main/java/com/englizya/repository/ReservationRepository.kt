package com.englizya.repository

import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest

interface ReservationRepository {
    suspend fun reserveSeats(paymentRequest: PaymentRequest, token : String): Result<List<ReservationTicket>>
}