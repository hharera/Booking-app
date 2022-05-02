package com.englizya.model.response

import com.englizya.model.model.User
import kotlinx.serialization.Serializable

@Serializable
data class ReservationOrder(
    val user: User,
    val orderId: Int,
    val uid: String
)