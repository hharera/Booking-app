package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Line(
    var lineId: Int,
    var lineName: String? = null,
    var road: Int? = null,
    var stations: List<Station>,
    var source: Branch,
    var destination: Branch,
    var linePath: String? = null,
    var lineStatus: String? = null,
)