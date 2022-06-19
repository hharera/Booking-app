package com.englyzia.booking.utils

sealed class PaymentMethod(val value: String) {
    object Card : PaymentMethod("card")
    object EnglizyaWallet : PaymentMethod("englizya_wallet")
    object FawryPayment : PaymentMethod("fawry_payment")
    object MeezaPayment : PaymentMethod("meezapayment")
}
