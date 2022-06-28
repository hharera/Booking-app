package com.englizya.api.impl

import com.englizya.api.AnnouncementService
import com.englizya.api.utils.Routing
import com.englizya.model.model.Announcement
import io.ktor.client.*
import io.ktor.client.request.*


class AnnouncementServiceImpl constructor(
    private val client: HttpClient
) : AnnouncementService {

    override suspend fun getAnnouncements(): List<Announcement> =
       client.get(Routing.GET_ANNOUNCEMENTS)

}