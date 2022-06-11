package com.englizya.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineItems(
    @SerialName("sku") var sku: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("unit_cost") var unitCost: Double? = null,
    @SerialName("quantity") var quantity: Int? = null,
    @SerialName("net_total") var netTotal: Double? = null,
    @SerialName("discount_rate") var discountRate: Int? = null,
    @SerialName("discount_amount") var discountAmount: Int? = null,
    @SerialName("tax_rate") var taxRate: Int? = null,
    @SerialName("tax_total") var taxTotal: Int? = null,
    @SerialName("total") var total: Double? = null
)