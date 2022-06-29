package com.englizya.repository.impl

import com.englizya.api.AnnouncementService
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository

class AnnouncementRepositoryImpl  constructor(
    private val announcementService: AnnouncementService
) : AnnouncementRepository{
    override suspend fun getAllAnnouncement(): Result<List<Announcement>> =kotlin.runCatching {
        announcementService.getAnnouncements()
    }

    override suspend fun getAnnouncementDetails(announcementId: String): Result<Announcement> =kotlin.runCatching {
        announcementService.getAnnouncementDetails(announcementId)
    }

}