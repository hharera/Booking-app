package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Announcement(
    val announcementId: Int,
    val announcementTitle: String,
    val announcementDescription: String,
    val announcementImageUrl: String,
    val startDate: String,
    val endDate: String,
)
