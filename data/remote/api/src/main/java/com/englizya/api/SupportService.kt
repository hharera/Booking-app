package com.englizya.api

import com.englizya.model.request.ComplaintRequest
import com.englizya.model.request.DriverReviewRequest

interface SupportService {
    suspend fun insertComplaint(
        complaintRequest: ComplaintRequest, token:
        String
    ): Int

    suspend fun insertDriverReview(
        driverReviewRequest: DriverReviewRequest,
        token: String
    ): Int
}