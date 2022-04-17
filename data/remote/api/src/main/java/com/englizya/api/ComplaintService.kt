package com.englizya.api

import com.englizya.model.model.Complaint

interface ComplaintService {
    suspend fun insertComplaint(complaint: Complaint): String
}