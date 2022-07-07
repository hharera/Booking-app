package com.englizya.local.di

import androidx.room.Room
import com.englizya.local.external_routes.ExternalRoutesDao
import com.englizya.local.external_routes.ExternalRoutesDatabase
import com.englizya.local.iInternal_routes.InternalRoutesDao
import com.englizya.local.iInternal_routes.InternalRoutesDatabase
import com.englizya.local.offers.OfferDao
import com.englizya.local.offers.OfferDatabase
import com.englizya.local.ticket.TicketDao
import com.englizya.local.ticket.TicketDatabase
import com.englizya.local.trip.TripDao
import com.englizya.local.trip.TripDatabase
import com.englizya.local.user.UserDao
import com.englizya.local.user.UserDatabase
import com.englizya.local.announcement.AnnouncementDao
import com.englizya.local.announcement.AnnouncementDatabase
import com.englizya.local.utils.Constants.ANNOUNCEMENT_DATA_BASE
import com.englizya.local.utils.Constants.EXTERNAL_ROUTES_DATA_BASE
import com.englizya.local.utils.Constants.INTERNAL_ROUTES_DATA_BASE
import com.englizya.local.utils.Constants.OFFER_DATA_BASE
import com.englizya.local.utils.Constants.TICKET_DATA_BASE
import com.englizya.local.utils.Constants.TRIP_DATA_BASE
import com.englizya.local.utils.Constants.USER_DATA_BASE
import org.koin.core.qualifier.named
import org.koin.dsl.module


val databaseModule = module {

    single<TicketDatabase> {
        Room.databaseBuilder(get(), TicketDatabase::class.java, TICKET_DATA_BASE).build()
    }

    single<TicketDao> {
        get<TicketDatabase>().getMarketDao()
    }

    single(named("user_dao")) {
        Room.databaseBuilder(get(), UserDatabase::class.java, USER_DATA_BASE)
            .build()
            .getDao()
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
        Room.databaseBuilder(get(), AnnouncementDatabase::class.java, ANNOUNCEMENT_DATA_BASE)
            .build()
    }
    single<InternalRoutesDao> {
        get<InternalRoutesDatabase>().getMarketDao()
    }
    single<InternalRoutesDatabase> {
        Room.databaseBuilder(get(), InternalRoutesDatabase::class.java, INTERNAL_ROUTES_DATA_BASE)
          .build()
    }

    single<ExternalRoutesDao> {
        get<ExternalRoutesDatabase>().getMarketDao()
    }
    single<ExternalRoutesDatabase> {
        Room.databaseBuilder(get(), ExternalRoutesDatabase::class.java, EXTERNAL_ROUTES_DATA_BASE)
          .build()
    }
    single<TripDao> {
        get<TripDatabase>().getMarketDao()
    }
    single<TripDatabase> {
        Room.databaseBuilder(get(), TripDatabase::class.java, TRIP_DATA_BASE)
            .build()
    }
}