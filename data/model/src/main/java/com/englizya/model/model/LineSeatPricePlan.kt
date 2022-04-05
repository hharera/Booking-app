package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class LineSeatPricePlan(
    val planId: Int? = null,
    val source: Int? = null,
    val destination: Int? = null,
    val vipPrice: Int? = null,
)