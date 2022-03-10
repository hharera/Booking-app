package com.englizya.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [Ticket::class, UnPrintedTicket::class],
    exportSchema = true,
)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getMarketDao(): TicketDao
}