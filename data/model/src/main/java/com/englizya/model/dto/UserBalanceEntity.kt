package com.englizya.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserBalance (
    var uid: String,
    var balance: Double,
)
