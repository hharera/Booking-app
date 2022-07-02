package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.englizya.model.converters.RouteStationTypeConverter
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "InternalRoutes")
@TypeConverters(RouteStationTypeConverter::class)
data class InternalRoutes(
    @PrimaryKey  var routeName: String,
    var routeStations:List<RouteStations>,

    )
