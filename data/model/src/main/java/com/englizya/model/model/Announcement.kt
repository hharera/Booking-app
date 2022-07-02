package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Announcement")
data class Announcement(
    @PrimaryKey val announcementId: Int,
    val announcementTitle: String,
    val announcementDescription: String,
    val announcementImageUrl: String,
    val startDate: String,
    val endDate: String,
)
