package com.englizya.model.response

import kotlinx.serialization.Serializable
@Serializable
data class UserEditResponse(
    val message: String,
    val status: String

)
