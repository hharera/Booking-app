package com.englizya.api.impl

import com.englizya.api.AnnouncementService
import com.englizya.model.model.Announcement
import io.ktor.client.*


class AnnouncementServiceImpl constructor(
    private val client: HttpClient
) : AnnouncementService {

    override suspend fun getAnnouncements(): List<Announcement> {
        TODO("Not yet implemented")
    }
}