package com.englizya.repository

import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.PayMobPaymentResponse

interface PaymentRepository {
    suspend fun requestPayment(request: PaymentRequest): Result<PayMobPaymentResponse>
}