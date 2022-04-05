package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class LinePricePlanMaster(
    val planId: Int? = null,
    val lineId: Int? = null,
    val seatPrices: List<LineSeatPricePlan>
)