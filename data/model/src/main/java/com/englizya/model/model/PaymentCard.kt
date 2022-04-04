package com.englizya.model.model

import java.io.Serializable

data class PaymentCard(
    val cardNumber: String,
    val cardCvv: String,
    val cardOwner: String,
    val cardExpiration: String,
) : Serializable