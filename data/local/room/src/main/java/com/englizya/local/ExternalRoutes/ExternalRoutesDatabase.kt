package com.englizya.local.ExternalRoutes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.englizya.local.converter.RouteStationTypeConverter
import com.englizya.model.model.ExternalRoutes

@Database(
    version = 1,
    entities = [ExternalRoutes::class],
    exportSchema = true,


    )
@TypeConverters(RouteStationTypeConverter::class)
abstract class ExternalRoutesDatabase : RoomDatabase() {
    abstract fun getMarketDao() : ExternalRoutesDao
}