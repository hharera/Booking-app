package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.Branch
import com.google.gson.Gson

class BranchTypeConverter {
    @TypeConverter
    fun fromBranchToGson(branch: Branch): String {
        return Gson().toJson(Branch)
    }

    @TypeConverter
    fun fromGsonToBranch(branch: String):Branch {
        return Gson().fromJson(branch,Branch::class.java)
    }
}