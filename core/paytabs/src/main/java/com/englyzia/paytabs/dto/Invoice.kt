package com.englyzia.paytabs.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Invoice(
    @SerialName("profile_id") var profileId: String? = null,
    @SerialName("tran_type") var tranType: String? = null,
    @SerialName("tran_class") var tranClass: String? = null,
    @SerialName("cart_currency") var cartCurrency: String? = null,
    @SerialName("cart_amount") var cartAmount: String? = null,
    @SerialName("cart_id") var cartId: String? = null,
    @SerialName("cart_description") var cartDescription: String? = null,
    @SerialName("hide_shipping") var hideShipping: Boolean? = null,
    @SerialName("customer_ref") var customerRef: String? = null,
    @SerialName("customer_details") var customerDetails: CustomerDetails? = CustomerDetails(),
    @SerialName("invoice") var invoice: InvoiceDetails? = InvoiceDetails(),
    @SerialName("callback") var callback: String? = null,
    @SerialName("return") var ret: String? = null,
    @SerialName("payment_methods") var paymentMethods: List<String>
)