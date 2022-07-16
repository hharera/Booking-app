package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class InvoicePaymentOrderRequest(
    val orderId: Int
)