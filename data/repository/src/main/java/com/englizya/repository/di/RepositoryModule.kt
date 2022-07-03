package com.englizya.repository.di

import com.englizya.repository.*
import com.englizya.repository.impl.*
import org.koin.dsl.module

val repositoryModule = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single<TripRepository> {
        TripRepositoryImpl(get())
    }

    single<BookingOfficeRepository> {
        BookingOfficeRepositoryImpl(get())
    }
    single<OfferRepository> {
       OfferRepositoryImpl(get())
    }
    single<AnnouncementRepository> {
        AnnouncementRepositoryImpl(get())
    }
    single<StationRepository> {
        StationRepositoryImpl(get())
    }
    single<RouteRepository> {
        RouteRepositoryImpl(get(),get(),get())
    }
    single<PaymentRepository> {
        PaymentRepositoryImpl(get())
    }

    single<ReservationRepository> {
        ReservationRepositoryImpl(get())
    }

    single<SupportRepository> {
        SupportRepositoryImpl(get())
    }

    single<TicketRepository> {
        TicketRepositoryImpl(get())
    }

    single<WalletRepository> {
        WalletRepositoryImpl(get())
    }
}
