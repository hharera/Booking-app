package com.englizya.model.model

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val uid: String,
    val username: String,
    val password: String,
    val phoneNumber: String,
    val name: String,
    private val address: String? = null
)