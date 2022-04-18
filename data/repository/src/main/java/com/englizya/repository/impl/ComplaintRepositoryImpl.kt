package com.englizya.repository.impl

import com.englizya.api.ComplaintService
import com.englizya.model.request.ComplaintRequest
import com.englizya.repository.ComplaintRepository

class ComplaintRepositoryImpl constructor(
    private val complaintService: ComplaintService
): ComplaintRepository {

    override fun insertComplaint(complaintRequest: ComplaintRequest): Result<Int> = kotlin.runCatching{
        complaintService.insertComplaint(complaintRequest)
    }

}