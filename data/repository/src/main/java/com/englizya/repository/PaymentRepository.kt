package com.englizya.repository

import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder

interface PaymentRepository {
    suspend fun requestPayment(request: PaymentRequest, token : String): Result<PayMobPaymentResponse>
    suspend fun requestPayment(token: String, request: PaymentOrderRequest): Result<PaymentOrder>
}