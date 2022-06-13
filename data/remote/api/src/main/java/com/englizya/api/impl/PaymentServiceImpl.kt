package com.englizya.api.impl

import com.englizya.api.PaymentService
import com.englizya.api.utils.Header.BEARER
import com.englizya.api.utils.Routing
import com.englizya.model.payment.FawryInvoice
import com.englizya.model.request.FawryPaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.FawryPaymentResponse
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.PaymentOrder
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class PaymentServiceImpl constructor(
    private val client: HttpClient
) : PaymentService {

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
        }
    }

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
        }
    }

    override suspend fun requestFawryPayment(request: FawryInvoice): FawryPaymentResponse {
        return client.post(Routing.REQUEST_FAWRY_PAYMENT) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(HttpHeaders.Authorization,"SRJNDG62ZD-JD96MLGDLH-NNNTM9WHGN")
        }
    }

    override suspend fun requestFawryPaymentOrder(request: FawryPaymentOrderRequest, token: String): Any? {
        return client.post(Routing.REQUEST_FAWRY_PAYMENT_ORDER) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(HttpHeaders.Authorization,"$BEARER $token")
        }
    }
}