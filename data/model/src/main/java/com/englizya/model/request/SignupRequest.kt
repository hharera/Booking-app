package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val uid: String,
    val phoneNumber: String,
    val password: String,
    val name: String,
)