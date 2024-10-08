package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "InternalRoutes")
data class InternalRoutes(
    var cityName : String,
    var routeName: String,
    var routeStations: List<RouteStations>,
    @PrimaryKey var lineCode : String ,

    )
