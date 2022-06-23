package com.englizya.model.model

import com.englizya.model.model.LineStation
import com.englizya.model.model.Station
import kotlinx.serialization.Serializable

@Serializable
data class LineDetails(
    var lineId: Int,
    var lineName:String,
    var source : Station,
    var destination : Station,
    var linePath : LineStation,
)
