package com.englizya.repository.utils

sealed class UserKey(val value: String) {
    data class Token(private val token: String) : UserKey(token)
    data class Username(private val uid: String) : UserKey(uid)
}
