package com.englizya.signup

data class SignupFormState(
    var passwordError: Int? = null,
    var phoneNumberError: Int? = null,
    var isValid: Boolean = false,
)