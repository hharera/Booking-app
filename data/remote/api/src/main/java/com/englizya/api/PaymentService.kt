package com.englizya.api

import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englyzia.paytabs.dto.Invoice

interface PaymentService {

    suspend fun requestPayment(request: PaymentRequest, token: String): PayMobPaymentResponse
    suspend fun requestPayment(token: String, request: PaymentOrderRequest): PaymentOrder
    suspend fun requestInvoicePaymentOrder(request: InvoicePaymentOrderRequest, token: String): Any?
    suspend fun requestInvoicePayment(request: Invoice): InvoicePaymentResponse
}