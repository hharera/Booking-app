package com.englizya.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShippingDetails(
    @SerialName("name") var name: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("phone") var phone: String? = null,
    @SerialName("street1") var street1: String? = null,
    @SerialName("city") var city: String? = null,
    @SerialName("state") var state: String? = null,
    @SerialName("country") var country: String? = null,
    @SerialName("zip") var zip: String? = null
)