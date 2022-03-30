package com.englizya.api

import com.englizya.model.dto.Announcement

interface AnnouncementService {

    suspend fun getAnnouncements(): List<Announcement>
}