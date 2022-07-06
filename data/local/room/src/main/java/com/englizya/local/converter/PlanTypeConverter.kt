package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.BookingOffice
import com.englizya.model.model.Plan
import com.google.gson.Gson

class PlanTypeConverter {
    @TypeConverter
    fun fromPlanToGson(plan: Plan): String {
        return Gson().toJson(plan)
    }

    @TypeConverter
    fun fromGsonToPlan(plan: String): Plan {
        return Gson().fromJson(plan, Plan::class.java)
    }
}