package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Offer")
data class Offer(
    @PrimaryKey val offerId: Int,
    val offerTitle: String,
    val offerDescription: String,
    val discount: Double,
    val offerImageUrl: String,
    val startDate: String,
    val endDate: String,
)
