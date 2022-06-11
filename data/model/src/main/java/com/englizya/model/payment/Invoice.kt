package com.englizya.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Invoice(
    @SerialName("shipping_charges") var shippingCharges: Int? = null,
    @SerialName("extra_charges") var extraCharges: Int? = null,
    @SerialName("extra_discount") var extraDiscount: Int? = null,
    @SerialName("total") var total: Int? = null,
    @SerialName("activation_date") var activationDate: String? = null,
    @SerialName("expiry_date") var expiryDate: String? = null,
    @SerialName("due_date") var dueDate: String? = null,
    @SerialName("line_items") var lineItems: ArrayList<LineItems> = arrayListOf()
)