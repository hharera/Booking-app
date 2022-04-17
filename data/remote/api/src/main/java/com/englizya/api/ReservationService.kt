package com.englizya.api

import com.englizya.model.model.ReservationTicket
import com.englizya.model.request.PaymentRequest

interface ReservationService {
    suspend fun bookSeats(paymentRequest: PaymentRequest, token: String): List<ReservationTicket>
}