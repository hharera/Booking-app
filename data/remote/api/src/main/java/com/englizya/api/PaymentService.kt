package com.englizya.api

import com.englizya.model.payment.FawryInvoice
import com.englizya.model.request.FawryPaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.FawryPaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder

interface PaymentService {

    suspend fun requestPayment(request: PaymentRequest, token: String): PayMobPaymentResponse
    suspend fun requestPayment(token: String, request: PaymentOrderRequest): PaymentOrder
    suspend fun requestFawryPaymentOrder(request: FawryPaymentOrderRequest, token: String): Any?
    suspend fun requestFawryPayment(request: FawryInvoice): FawryPaymentResponse
}