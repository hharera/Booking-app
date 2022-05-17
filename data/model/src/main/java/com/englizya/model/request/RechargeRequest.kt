package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RechargeRequest(
    val transactionRef : String,
)
