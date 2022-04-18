package com.englizya.model.request

import io.ktor.http.cio.*

data class ComplaintRequest(
    var complaintTitle: String,
    var complaintDesc: String,
    var complaintImage: MultipartEvent.MultipartPart? = null,
)