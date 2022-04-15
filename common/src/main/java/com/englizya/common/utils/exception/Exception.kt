package com.englizya.common.utils.exception

sealed class CustomException() {
    object AuthorizationException : CustomException()
}
