package com.englizya.repository.impl

import com.englizya.api.PaymentService
import com.englizya.model.payment.FawryInvoice
import com.englizya.model.request.FawryPaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.FawryPaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englizya.repository.PaymentRepository

class PaymentRepositoryImpl  constructor(
    private val paymentService: PaymentService
) : PaymentRepository {

    override suspend fun requestPayment(
        request: PaymentRequest,
        token: String
    ): Result<PayMobPaymentResponse> =
        kotlin.runCatching {
            paymentService.requestPayment(request, token)
        }

    override suspend fun requestPayment(
        token: String,
        request: PaymentOrderRequest
    ): Result<PaymentOrder> =
        kotlin.runCatching {
            paymentService.requestPayment(token, request)
        }

    override suspend fun requestFawryPayment(request: FawryInvoice): Result<FawryPaymentResponse> =
        kotlin.runCatching {
            paymentService.requestFawryPayment(request)
        }

    override suspend fun requestFawryPaymentOrder(request: FawryPaymentOrderRequest, token: String): Result<Any?> =
        kotlin.runCatching {
            paymentService.requestFawryPaymentOrder(request, token)
        }
}