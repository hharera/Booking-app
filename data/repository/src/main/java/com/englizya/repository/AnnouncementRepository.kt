package com.englizya.repository

import com.englizya.model.model.Announcement
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {

    fun getAllAnnouncement(forceOnline: Boolean = false): Flow<Resource<List<Announcement>>>
    fun getAnnouncement(announcementId: Int, forceOnline: Boolean = false): Flow<Resource<Announcement>>
}