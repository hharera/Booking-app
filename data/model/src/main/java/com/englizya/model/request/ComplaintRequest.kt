package com.englizya.model.request

import java.io.File

data class ComplaintRequest(
    var complaintTitle: String,
    var complaintDesc: String,
    var complaintImage: File? = null,
)