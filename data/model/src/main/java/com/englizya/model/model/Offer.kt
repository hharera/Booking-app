package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val offerId: Int,
    val offerTitle: String,
    val offerDescription: String,
    val discount: Double,
    val offerImageUrl: String,
    val startDate: String,
    val endDate: String,
    )
