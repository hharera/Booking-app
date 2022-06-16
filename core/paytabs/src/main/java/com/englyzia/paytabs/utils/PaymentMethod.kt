package com.englyzia.paytabs.utils

sealed class PaymentMethod(val name: String) {
    object Fawry : PaymentMethod("fawry")
    object Meeza : PaymentMethod("meezaqr")
}
