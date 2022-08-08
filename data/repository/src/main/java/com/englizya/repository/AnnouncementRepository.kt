package com.englizya.repository

import com.englizya.model.model.Announcement
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {

    fun getAllAnnouncement(): Flow<Resource<List<Announcement>>>
    fun getAnnouncement(announcementId: Int, forceOnline: Boolean = true): Flow<Resource<Announcement>>
    suspend fun insertAnnouncement(announcement: Announcement): Result<Unit>
    suspend fun insertAnnouncements(announcements: List<Announcement>): Result<Unit>
}