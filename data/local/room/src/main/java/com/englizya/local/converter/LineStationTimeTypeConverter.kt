package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.LineStation
import com.englizya.model.model.LineStationTime
import com.google.gson.Gson

class LineStationTimeTypeConverter {
    @TypeConverter
    fun fromLineStationTimeToGson(lineStationTime: ArrayList<LineStationTime>): String {
        return Gson().toJson(lineStationTime)
    }

    @TypeConverter
    fun fromGsonToLineStationTimes(lineStationTime: String): ArrayList<LineStationTime> {
        return ArrayList(Gson().fromJson(lineStationTime,Array<LineStationTime>::class.java).toMutableList())
    }
}