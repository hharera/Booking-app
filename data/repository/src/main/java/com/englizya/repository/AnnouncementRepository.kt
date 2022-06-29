package com.englizya.repository

import com.englizya.model.model.Announcement

interface AnnouncementRepository {
    suspend fun getAllAnnouncement(): Result<List<Announcement>>
    suspend fun getAnnouncementDetails(announcementId: String): Result<Announcement>

}