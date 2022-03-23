package com.englizya.model.dto

import java.io.Serializable

data class PaymentCard(
    val cardNumber: String,
    val cardCvv: String,
    val cardOwner: String,
    val cardExpiration: String,
) : Serializable