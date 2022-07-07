package com.englizya.local.ticket

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.response.UserTicket


@Database(
    version = 1,
    entities = [UserTicket::class],
    exportSchema = true,
)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getMarketDao(): TicketDao
}