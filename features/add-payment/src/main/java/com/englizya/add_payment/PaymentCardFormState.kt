package com.englizya.add_payment

data class PaymentCardFormState(
    var cardNumberError: Int? = null,
    var cardCvvError: Int? = null,
    var cardExpirationError: Int? = null,
    var cardOwnerError: Int? = null,
    var isValid: Boolean = false,
)