package com.englizya.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FawryPaymentResponse(
    @SerialName("invoice_id") var invoiceId: Long? = null,
    @SerialName("invoice_link") var invoiceLink: String? = null,
)