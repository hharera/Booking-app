package com.englizya.model.request

import java.io.File

data class DriverReviewRequest(
    val review: Int,
    val driverCode: Int,
    val reviewMessage: String,
    var complaintImage: File? = null,
)
