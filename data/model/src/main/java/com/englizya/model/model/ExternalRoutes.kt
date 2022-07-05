package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "ExternalRoutes")
data class ExternalRoutes(
    @PrimaryKey var lineCode : Int ,
    var routeName: String,
    var routeStations: List<RouteStations>,

    )
