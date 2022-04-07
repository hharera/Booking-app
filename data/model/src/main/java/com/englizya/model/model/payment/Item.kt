package com.englizya.model.model.payment

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerializedName("name") var name: String,
    @SerializedName("amount_cents") var amountCents: String,
    @SerializedName("description") var description: String,
    @SerializedName("quantity") var quantity: String
)