package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    var uid: String,
    var phoneNumber: String,
    var password: String,
)
