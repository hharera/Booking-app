package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PaymentOrderRequest(
    val amount: Double,
)
