package com.englizya.repository

import com.englizya.model.request.ComplaintRequest

interface ComplaintRepository {
    fun insertComplaint(complaintRequest: ComplaintRequest): Result<Int>
}