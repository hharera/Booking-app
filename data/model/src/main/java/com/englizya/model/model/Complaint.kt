package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Complaint(
    var complaintId: Int,
    var username: String,
    var complaintDesc: String,
    var complaintImageUrl: String,
    var complaintTitle: String,
    var complaintTime: String,
)

