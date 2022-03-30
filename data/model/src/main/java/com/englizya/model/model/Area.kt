package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Area (
    var areaId: Int? = null,
    var areaName: String? = null,
    var createdBy: String? = null,
    var creationDate: String? = null,
)