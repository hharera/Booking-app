package com.englizya.api

import com.englizya.model.model.Announcement

interface AnnouncementService {

    suspend fun getAnnouncements(): List<Announcement>
}