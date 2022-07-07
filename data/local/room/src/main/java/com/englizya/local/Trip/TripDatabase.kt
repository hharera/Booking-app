package com.englizya.local.trip

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.englizya.local.converter.*
import com.englizya.model.model.Trip

@Database(
    version = 1,
    entities = [Trip::class],
    exportSchema = true,
)
@TypeConverters(
    BookingOfficeTypeConverter::class,
    BookingOfficeListTypeConverter::class,
    BranchTypeConverter::class,
    LineStationTypeConverter::class,
    LineStationTimeTypeConverter::class,
    PlanTypeConverter::class,
    ReservationsTypeConverter::class,
    SeatPricesTypeConverter::class,
    SeatTypeConverter::class,
    ServiceDegreeTypeConverter::class,
)
abstract class TripDatabase : RoomDatabase() {
    abstract fun getMarketDao(): TripDao
}

