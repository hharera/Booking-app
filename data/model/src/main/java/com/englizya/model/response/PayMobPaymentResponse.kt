package com.englizya.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayMobPaymentResponse(
    @SerialName(value = "token") val token: String
)