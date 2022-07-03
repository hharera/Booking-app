package com.englizya.repository.impl

import android.util.Log
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
                announcementService.getAnnouncements().also {
                    announcementDao.insertAnnouncements(it)
                    Log.d("DataRemote",it.toString())

                }
            } else {
                announcementDao.getAnnouncements().also {
                    Log.d("DataLocal",it.toString())
                }

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