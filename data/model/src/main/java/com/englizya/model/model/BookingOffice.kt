package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class BookingOffice (
    var officeId: Int,
    var area: Area,
    var officeName: String? = null,
)