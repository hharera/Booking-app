package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PaymentOrder(
    val orderId : Int,
)