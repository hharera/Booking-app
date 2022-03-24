package com.englizya.api

import com.englizya.model.dto.Announcement
import com.englizya.model.dto.User
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.model.response.LoginResponse

interface RemoteAnnouncementService {

    suspend fun getAnnouncements(): List<Announcement>
}