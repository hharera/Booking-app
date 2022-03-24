package com.englizya.api.impl

import com.englizya.api.RemoteAnnouncementService
import com.englizya.model.dto.Announcement
import io.ktor.client.*
import javax.inject.Inject


class RemoteAnnouncementServiceImpl @Inject constructor(
    private val client: HttpClient
) : RemoteAnnouncementService {

    override suspend fun getAnnouncements(): List<Announcement> {
        TODO("Not yet implemented")
    }
}