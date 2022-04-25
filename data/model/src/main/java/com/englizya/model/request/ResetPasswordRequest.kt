package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val authenticationId: String,
    val password: String,
)
