package com.englizya.utils

data class LoginFormState(
    var phoneNumberError: Int? = null,
    var passwordError: Int? = null,
    var formIsValid: Boolean = false,
)
