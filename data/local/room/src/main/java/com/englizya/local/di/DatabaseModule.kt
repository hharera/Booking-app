package com.englizya.local.di

import androidx.room.Room
import com.englizya.local.TicketDao
import com.englizya.local.TicketDatabase
import com.englizya.local.utils.Constants.TICKET_DATA_BASE
import org.koin.dsl.module


val databaseModule = module {

    single<TicketDatabase> {
        Room.databaseBuilder(get(), TicketDatabase::class.java, TICKET_DATA_BASE).build()
    }

    single<TicketDao> {
        get<TicketDatabase>().getMarketDao()
    }
}