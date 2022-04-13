package com.englizya.api

import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.PayMobPaymentResponse

interface PaymentService {

    suspend fun requestPayment(request: PaymentRequest): PayMobPaymentResponse
}