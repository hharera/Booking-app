package com.englizya.api.impl

import com.englizya.api.AnnouncementService
import com.englizya.model.dto.Announcement
import io.ktor.client.*
import javax.inject.Inject


class AnnouncementServiceImpl @Inject constructor(
    private val client: HttpClient
) : AnnouncementService {

    override suspend fun getAnnouncements(): List<Announcement> {
        TODO("Not yet implemented")
    }
}