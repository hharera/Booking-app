package com.englizya.repository.di

import com.englizya.repository.*
import com.englizya.repository.impl.*
import org.koin.dsl.module

val repositoryModule = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get(), get())
    }

    single<TripRepository> {
        TripRepositoryImpl(get(),get())
    }

    single<BookingOfficeRepository> {
        BookingOfficeRepositoryImpl(get())
    }
    single<OfferRepository> {
       OfferRepositoryImpl(get(),get(),get())
    }
    single<AnnouncementRepository> {
        AnnouncementRepositoryImpl(get(),get(), get())
    }
    single<StationRepository> {
        StationRepositoryImpl(get())
    }
    single<RouteRepository> {
        RouteRepositoryImpl(get(),get(),get(),get(),get())
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
        TicketRepositoryImpl(get(),get())
    }

    single<WalletRepository> {
        WalletRepositoryImpl(get())
    }
}
