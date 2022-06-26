package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Routes(
    var routeName: String,
    var routeStations:List<RouteStations>,

)
