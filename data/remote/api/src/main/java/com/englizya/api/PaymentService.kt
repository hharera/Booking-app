package com.englizya.api

import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder

interface PaymentService {

    suspend fun requestPayment(request: PaymentRequest, token: String): PayMobPaymentResponse
    suspend fun requestPayment(token: String, request: PaymentOrderRequest): PaymentOrder
}