package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteStations(
    val stationName : String ,
    val stationOrder : Int,
)
