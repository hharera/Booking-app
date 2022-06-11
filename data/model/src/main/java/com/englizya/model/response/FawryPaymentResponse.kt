package com.englizya.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FawryPaymentResponse(
    @SerialName("tran_ref") var tranRef: String? = null,
    @SerialName("tran_type") var tranType: String? = null,
    @SerialName("cart_id") var cartId: String? = null,
    @SerialName("cart_description") var cartDescription: String? = null,
    @SerialName("cart_currency") var cartCurrency: String? = null,
    @SerialName("cart_amount") var cartAmount: String? = null,
    @SerialName("return") var returnUrl: String? = null,
    @SerialName("redirect_url") var redirectUrl: String? = null,
    @SerialName("customer_details") var customerDetails: CustomerDetails?,
    @SerialName("shipping_details") var shippingDetails: ShippingDetails?
)