package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    var serviceId: Int,
    var serviceName: String,
)