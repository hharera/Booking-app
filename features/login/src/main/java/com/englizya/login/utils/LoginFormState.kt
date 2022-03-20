package com.englizya.login.utils

data class LoginFormState(
    var phoneNumberError: Int? = null,
    var passwordError: Int? = null,
    var formIsValid: Boolean = false,
)
