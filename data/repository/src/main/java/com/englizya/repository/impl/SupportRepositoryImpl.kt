package com.englizya.repository.impl

import com.englizya.api.SupportService
import com.englizya.model.request.ComplaintRequest
import com.englizya.model.request.DriverReviewRequest
import com.englizya.repository.SupportRepository

class SupportRepositoryImpl constructor(
    private val supportService: SupportService,
): SupportRepository {

    override suspend fun insertComplaint(
        complaintRequest: ComplaintRequest,
        token: String
    ): Result<Int> = kotlin.runCatching{
        supportService.insertComplaint(complaintRequest, token)
    }

    override suspend fun insertDriverReview(
        driverReviewRequest: DriverReviewRequest,
        token: String
    ): Result<Int> = kotlin.runCatching{
        supportService.insertDriverReview(
            driverReviewRequest,
            token
        )
    }

}