package com.englizya.model.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.englizya.model.model.RouteStations
import org.json.JSONObject

@ProvidedTypeConverter
class RouteStationTypeConverter {
    @TypeConverter
    fun fromStationToString(station: RouteStations): String {
        return JSONObject().apply {
            put("stationName", station.stationName)
            put("stationOrder", station.stationOrder)
        }.toString()
    }

    @TypeConverter
    fun fromStringToStation(station: String): RouteStations {
        val json = JSONObject(station)
        return RouteStations(json.getString("stationName"), json.getInt("stationOrder"))
    }
}
