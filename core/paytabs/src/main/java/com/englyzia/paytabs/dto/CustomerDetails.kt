package com.englyzia.paytabs.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDetails(
    @SerialName("name") var name: String? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("street1") var street1: String? = null,
    @SerialName("city") var city: String? = null,
    @SerialName("country") var country: String? = null
)