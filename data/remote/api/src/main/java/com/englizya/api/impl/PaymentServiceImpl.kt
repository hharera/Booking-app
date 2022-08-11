package com.englizya.api.impl

import com.englizya.api.PaymentService
import com.englizya.api.utils.Header.BEARER
import com.englizya.api.utils.Routing
import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englyzia.paytabs.dto.Invoice
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class PaymentServiceImpl constructor(
    private val client: HttpClient
) : PaymentService {

    @OptIn(InternalAPI::class)
    override suspend fun requestPayment(
        request: PaymentRequest,
        token: String
    ): PayMobPaymentResponse {
        return client.post(Routing.REQUEST_RECHARGE) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(
                HttpHeaders.Authorization,
                "$BEARER $token"
            )
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun requestPayment(
        token: String,
        request: PaymentOrderRequest
    ): PaymentOrder {
        return client.post(Routing.REQUEST_RECHARGE) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(
                HttpHeaders.Authorization,
                "$BEARER $token"
            )
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun requestInvoicePayment(request: Invoice): InvoicePaymentResponse {
        return client.post(Routing.REQUEST_INVOICE_PAYMENT) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(HttpHeaders.Authorization,"SRJNDG62ZD-JD96MLGDLH-NNNTM9WHGN")
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun requestInvoicePaymentOrder(request: InvoicePaymentOrderRequest, token: String): Any? {
        return client.post(Routing.REQUEST_INVOICE_PAYMENT_ORDER) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(HttpHeaders.Authorization,"$BEARER $token")
        }.body()
    }
}