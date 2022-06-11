package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FawryPaymentOrderRequest(
    val orderId: Int
)