package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    var phoneNumber: String,
    var password: String,
)
