package com.englizya.repository.impl

import com.englizya.api.PaymentService
import com.englizya.model.request.PaymentRequest
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentService: PaymentService
) : PaymentRepository {

    override suspend fun requestPayment(request: PaymentRequest): Result<PayMobPaymentResponse> =
        kotlin.runCatching {
            paymentService.requestPayment(request)
        }
}