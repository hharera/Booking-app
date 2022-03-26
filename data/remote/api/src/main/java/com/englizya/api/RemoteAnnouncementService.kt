package com.englizya.api

import com.englizya.model.dto.Announcement

interface RemoteAnnouncementService {

    suspend fun getAnnouncements(): List<Announcement>
}