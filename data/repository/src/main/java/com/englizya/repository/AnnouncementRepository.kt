package com.englizya.repository

import com.englizya.model.model.Announcement

interface AnnouncementRepository {

    suspend fun getAllAnnouncement(forceOnline: Boolean = false): Result<List<Announcement>>
    suspend fun getAnnouncementDetails(announcementId: String, forceOnline: Boolean = false): Result<Announcement>
}