package com.englyzia.booking.utils

sealed class PaymentMethod {
    object Card : PaymentMethod()
    object EnglizyaWallet : PaymentMethod()
    object FawryPayment : PaymentMethod()
    object MeezaPayment : PaymentMethod()
}
