package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class LineCode(
    var lineId: Int,
    var lineName: String?= null,
    var road: Int?= null,
    var doCity: Int?= null,
    var arrivalCity: Int?= null,
    var linePath: String?= null,
    var lineStatus: String?= null,
)