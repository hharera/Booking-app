package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.ServiceDegree
import com.google.gson.Gson

class ServiceDegreeTypeConverter {
    @TypeConverter
    fun fromServiceDegreeToGson(serviceDegree: ServiceDegree): String {
        return Gson().toJson(serviceDegree)
    }

    @TypeConverter
    fun fromGsonToServiceDegree(serviceDegree: String): ServiceDegree {
        return Gson().fromJson(serviceDegree, ServiceDegree::class.java)
    }
}