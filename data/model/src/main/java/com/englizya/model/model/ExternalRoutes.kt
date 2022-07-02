package com.englizya.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.englizya.model.converters.RouteStationTypeConverter
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "ExternalRoutes")
@TypeConverters(RouteStationTypeConverter::class)
data class ExternalRoutes(
    @PrimaryKey var routeName: String,
   var routeStations:List<RouteStations>,

    )
