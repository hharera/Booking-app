package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class LineStation(
    var stationOrder: Int? = null,
    var lineId: Int? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var pathType: Int? = null,
    var lineIcon: String? = null,
    var branch: Station? = null,
)