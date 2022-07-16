package com.englizya.signup

data class SignupFormState(
    var phoneNumberError: Int? = null,
    var termsAcceptanceError: Int? = null,
    var isValid: Boolean = false,
)