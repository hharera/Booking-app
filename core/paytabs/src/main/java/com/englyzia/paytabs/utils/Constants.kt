package com.englyzia.paytabs.utils

import com.payment.paymentsdk.integrationmodels.PaymentSdkLanguageCode
import com.payment.paymentsdk.integrationmodels.PaymentSdkTokenise

object Currency {
    const val EG = "EGP"
}

object CountryCode {
    const val EG = "EG"
}

object Payment {
    const val profileId = "94307"
    const val serverKey = "SJJNDG62RM-JDHRGDH6LT-2Z6MDWGW6R"
    const val clientLey = "CKKMBP-9DRH6D-HRTDHG-6NKGKQ"
    val locale = PaymentSdkLanguageCode.EN
    const val screenTitle = "Test SDK"
    const val cartId = "123456"
    const val cartDesc = "cart description"
    val currency = Currency.EG
    val amount = 20.0
    val tokeniseType = PaymentSdkTokenise.NONE
}