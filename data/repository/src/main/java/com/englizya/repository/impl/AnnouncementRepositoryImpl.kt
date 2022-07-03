package com.englizya.repository.impl

import com.englizya.api.AnnouncementService
import com.englizya.local.announcement.AnnouncementDao
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository

class AnnouncementRepositoryImpl constructor(
    private val announcementService: AnnouncementService,
    private val announcementDao: AnnouncementDao,
) : AnnouncementRepository {

    override suspend fun getAllAnnouncement(forceOnline: Boolean): Result<List<Announcement>> =
        kotlin.runCatching {
            if (forceOnline) {
                announcementService.getAnnouncements()
            } else {
                announcementDao.getAnnouncements()
            }
        }

    override suspend fun getAnnouncementDetails(
        announcementId: String,
        forceOnline: Boolean
    ): Result<Announcement> = kotlin.runCatching {
        if (forceOnline) {
            announcementService.getAnnouncementDetails(announcementId)
        } else {
            announcementDao.getAnnouncement(announcementId)
        }
    }
}