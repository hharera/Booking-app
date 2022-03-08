package com.englizya.login

data class LoginFormState(
    var passwordError: Int? = null,
    var usernameError: Int? = null,
    var isValid: Boolean = false,
)