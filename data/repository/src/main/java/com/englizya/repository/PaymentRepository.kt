package com.englizya.repository

import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englyzia.paytabs.dto.Invoice

interface PaymentRepository {
    suspend fun requestPayment(
        request: PaymentRequest,
        token: String
    ): Result<PayMobPaymentResponse>

    suspend fun requestPayment(token: String, request: PaymentOrderRequest): Result<PaymentOrder>
    suspend fun requestInvoicePayment(request: Invoice): Result<InvoicePaymentResponse>
    suspend fun requestInvoicePaymentOrder(
        request: InvoicePaymentOrderRequest,
        token: String
    ): Result<Any?>
}