package com.englizya.repository.impl

import com.englizya.api.PaymentService
import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englizya.repository.PaymentRepository
import com.englyzia.paytabs.dto.Invoice

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

    override suspend fun requestInvoicePayment(request: Invoice): Result<InvoicePaymentResponse> =
        kotlin.runCatching {
            paymentService.requestInvoicePayment(request)
        }

    override suspend fun requestInvoicePaymentOrder(request: InvoicePaymentOrderRequest, token: String): Result<Any?> =
        kotlin.runCatching {
            paymentService.requestInvoicePaymentOrder(request, token)
        }
}