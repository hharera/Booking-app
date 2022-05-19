package com.englyzia.booking.utils

public sealed class PaymentMethod {
    object Card : PaymentMethod()
    object EnglizyaWallet : PaymentMethod()
}
