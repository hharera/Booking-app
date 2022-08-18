package com.englizya.repository.impl

import androidx.room.withTransaction
import com.englizya.api.AnnouncementService
import com.englizya.local.announcement.AnnouncementDao
import com.englizya.local.announcement.AnnouncementDatabase
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.utils.Resource
import com.englizya.repository.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

class AnnouncementRepositoryImpl constructor(
    private val announcementService: AnnouncementService,
    private val announcementDatabase: AnnouncementDatabase,
    private val announcementDao: AnnouncementDao,
) : AnnouncementRepository {

    override fun getAllAnnouncement(forceOnline: Boolean): Flow<Resource<List<Announcement>>> =
        networkBoundResource(
            query = {
                announcementDao.getAnnouncements()
            },
            fetch = {
                announcementService.getAnnouncements()
            },
            saveFetchResult = { announcements ->
                announcementDatabase.withTransaction {
                    announcementDao.clearAnnouncements()
                    announcementDao.insertAnnouncements(announcements)
                }
            },
            shouldFetch = {
                forceOnline
            }
        )

    override fun getAnnouncement(
        announcementId: Int,
        forceOnline: Boolean
    ) = networkBoundResource(
        query = {
            announcementDao.getAnnouncement(announcementId)
        },
        fetch = {
            announcementService.getAnnouncement(announcementId)
        },
        saveFetchResult = { announcement ->
            announcementDatabase.withTransaction {
                announcementDao.deleteAnnouncement(announcement)
                announcementDao.insertAnnouncement(announcement)
            }
        },
        shouldFetch = {
            forceOnline
        }
    )
}