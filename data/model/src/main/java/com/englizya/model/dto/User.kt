package com.englizya.model.dto

data class User(
    val uid: String,
    val username: String,
    val password: String,
    val phoneNumber: String,
    val name: String,
    private val address: String? = null
)