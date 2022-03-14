package com.englizya.local

import androidx.room.Database
import androidx.room.RoomDatabase




//TODO : uncomment database annotation to add schema
//@Database(
//    version = 1,
//    entities = [],
//    exportSchema = true,
//)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getMarketDao(): TicketDao
}