package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CancelTicketResponse(
    val message: String,
    val status: String
)
