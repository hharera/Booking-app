package com.englizya.model.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stations(
    @SerialName("stationOrder") var stationOrder: Int? = null,
    @SerialName("lineId") var lineId: Int? = null,
    @SerialName("pathType") var pathType: Int? = null,
    @SerialName("lineIcon") var lineIcon: String? = null,
    @SerialName("branch") var branch: Branch? = Branch()
)