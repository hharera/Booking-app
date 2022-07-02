package com.englizya.local.di

import androidx.room.Room
import com.englizya.local.Offers.OfferDao
import com.englizya.local.Offers.OfferDatabase
import com.englizya.local.TicketDao
import com.englizya.local.TicketDatabase
import com.englizya.local.UserDao
import com.englizya.local.UserDatabase
import com.englizya.local.announcement.AnnouncementDao
import com.englizya.local.announcement.AnnouncementDatabase
import com.englizya.local.utils.Constants.ANNOUNCEMENT_DATA_BASE
import com.englizya.local.utils.Constants.OFFER_DATA_BASE
import com.englizya.local.utils.Constants.TICKET_DATA_BASE
import com.englizya.local.utils.Constants.USER_DATA_BASE
import org.koin.dsl.module


val databaseModule = module {

    single<TicketDatabase> {
        Room.databaseBuilder(get(), TicketDatabase::class.java, TICKET_DATA_BASE).build()
    }

    single<TicketDao> {
        get<TicketDatabase>().getMarketDao()
    }


    single<UserDatabase> {
        Room.databaseBuilder(get(), UserDatabase::class.java, USER_DATA_BASE).build()
    }

    single<UserDao> {
        get<UserDatabase>().getMarketDao()
    }

    single<OfferDao> {
        get<OfferDatabase>().getMarketDao()
    }
    single<OfferDatabase> {
        Room.databaseBuilder(get(), OfferDatabase::class.java, OFFER_DATA_BASE).build()
    }
    single<AnnouncementDao> {
        get<AnnouncementDatabase>().getMarketDao()
    }
    single<AnnouncementDatabase> {
        Room.databaseBuilder(get(), AnnouncementDatabase::class.java, ANNOUNCEMENT_DATA_BASE).build()
    }
}