package com.englizya.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.englizya.model.model.RouteStations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RouteStationTypeConverter {
    @TypeConverter
    fun fromStationToGson(stations: List<RouteStations>): String {
        return Gson().toJson(stations)
    }

    @TypeConverter
    fun fromGsonToStation(station: String): List<RouteStations> {
//        val listType = object :
//            TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(station,Array<RouteStations>::class.java).toList()
    }
}
