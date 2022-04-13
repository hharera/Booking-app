package com.englizya.api.impl

import com.englizya.api.PaymentService
import com.englizya.api.utils.Routing
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.PayMobPaymentResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class PaymentServiceImpl @Inject constructor(
    private val client : HttpClient
): PaymentService {

    override suspend fun requestPayment(request: PaymentRequest): PayMobPaymentResponse {
        return client.post(Routing.REQUEST_PAYMENT) {
            contentType(ContentType.Application.Json)
            body = request
        }
    }
}