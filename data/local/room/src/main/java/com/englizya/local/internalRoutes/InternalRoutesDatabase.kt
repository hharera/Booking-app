package com.englizya.local.internalRoutes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.englizya.local.converter.RouteStationTypeConverter
import com.englizya.model.model.InternalRoutes

@Database(
    version = 6,
    entities = [InternalRoutes::class],
    exportSchema = true,
)
@TypeConverters(RouteStationTypeConverter::class)
abstract class InternalRoutesDatabase : RoomDatabase() {
    abstract fun getMarketDao(): InternalRoutesDao
}