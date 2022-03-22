package com.englizya.feature.set_password

data class PasswordFormState(
    var passwordError: Int? = null,
    var phoneNumberError: Int? = null,
    var isValid: Boolean = false,
)