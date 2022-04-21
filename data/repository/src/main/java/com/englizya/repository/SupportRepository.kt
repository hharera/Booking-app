package com.englizya.repository

import com.englizya.model.request.ComplaintRequest
import com.englizya.model.request.DriverReviewRequest

interface SupportRepository {
    suspend fun insertComplaint(complaintRequest: ComplaintRequest): Result<Int>
    suspend fun insertDriverReview(driverReviewRequest: DriverReviewRequest): Result<Int>
}