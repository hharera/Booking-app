package com.englizya.charging.utils

sealed class RechargeMethod(val value: String) {
    object Card : RechargeMethod("card")
    object FawryPayment : RechargeMethod("fawry_payment")
    object MeezaPayment : RechargeMethod("meezapayment")
    object VodafonePayment : RechargeMethod("vodafonepayment")
    object EtisalatPayment : RechargeMethod("etisalatpayment")
    object OrangePayment : RechargeMethod("orangepayment")

}
