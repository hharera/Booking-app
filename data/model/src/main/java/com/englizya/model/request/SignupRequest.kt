package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    var phoneNumber: String,
    var uid: String,
    var password: String,
)
