package com.englizya.repository.impl

import androidx.room.withTransaction
import com.englizya.api.AnnouncementService
import com.englizya.local.announcement.AnnouncementDao
import com.englizya.local.announcement.AnnouncementDatabase
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.utils.Resource
import com.englizya.repository.utils.networkBoundResource
//import com.englizya.repository.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

class AnnouncementRepositoryImpl constructor(
    private val announcementService: AnnouncementService,
    private val db: AnnouncementDatabase,
    private val announcementDao: AnnouncementDao,
) : AnnouncementRepository {

//    override suspend fun getAllAnnouncement(forceOnline: Boolean): Result<List<Announcement>> =
//        kotlin.runCatching {
//            if (forceOnline) {
//                announcementService.getAnnouncements().also {
//                    insertAnnouncements(it)
//                }
//            } else {
//                announcementDao.getAnnouncements()
//            }
//        }

    override fun getAllAnnouncement(): Flow<Resource<List<Announcement>>> = networkBoundResource(
        query = {
            announcementDao.getAnnouncements()
        },
        fetch = {
            announcementService.getAnnouncements()
        },
        saveFetchResult = { announcements ->
            db.withTransaction {
                announcementDao.clearAnnouncements()
                announcementDao.insertAnnouncements(announcements)
            }


        }
    )

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

//    override fun getAnnouncementDetails(announcementId: String): Flow<Resource<Announcement>> =
//        networkBoundResource(query = {
//            announcementDao.getAnnouncement(announcementId)
//        }, fetch = {
//            announcementService.getAnnouncementDetails(announcementId)
//        },
//            saveFetchResult = {
//                announcement->
//                db.withTransaction {
//                    announcementDao.deleteAnAnnouncement(announcementId)
//                    announcementDao.insertAnnouncement(announcement)
//                }
//            }
//        )


    override suspend fun insertAnnouncements(
        announcements: List<Announcement>
    ): Result<Unit> = kotlin.runCatching {
        announcementDao.clearAnnouncements()

        announcements.map {
            announcementDao.insertAnnouncement(it)
        }
    }

    override suspend fun insertAnnouncement(
        announcement: Announcement
    ): Result<Unit> = kotlin.runCatching {
        announcementDao.insertAnnouncement(announcement)
    }
}