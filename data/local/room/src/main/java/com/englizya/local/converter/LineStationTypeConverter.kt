package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.LineStation
import com.google.gson.Gson

class LineStationTypeConverter {
    @TypeConverter
    fun fromLineStationToGson(lineStations: ArrayList<LineStation>): String {
        return Gson().toJson(lineStations)
    }

    @TypeConverter
    fun fromGsonToLineStation(station: String): ArrayList<LineStation> {
        return ArrayList(Gson().fromJson(station,Array<LineStation>::class.java).toMutableList())
    }
}