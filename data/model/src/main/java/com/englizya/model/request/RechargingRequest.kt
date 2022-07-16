package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RechargingRequest(
    val transactionRef: String,
)
