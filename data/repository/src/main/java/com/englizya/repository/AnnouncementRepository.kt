package com.englizya.repository

import com.englizya.model.model.Announcement
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {

     fun getAllAnnouncement(): Flow<Resource<List<Announcement>>>
//     fun getAnnouncementDetails(announcementId: String): Flow<Resource<Announcement>>

    suspend fun getAnnouncementDetails(announcementId: String, forceOnline: Boolean = false): Result<Announcement>
    suspend fun insertAnnouncement(announcement: Announcement): Result<Unit>
    suspend fun insertAnnouncements(announcements: List<Announcement>): Result<Unit>
}