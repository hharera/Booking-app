package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChangeRequest(
    var uid: String,
    var oldPassword: String,
    var newPassword: String,
)
