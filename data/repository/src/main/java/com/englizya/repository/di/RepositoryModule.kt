package com.englizya.repository.di

import com.englizya.api.UserService
import com.englizya.local.user.UserDao
import com.englizya.repository.*
import com.englizya.repository.impl.*
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

val repositoryModule = module {

    single<TripRepository> {
        TripRepositoryImpl(get(),get())
    }

    single<BookingOfficeRepository> {
        BookingOfficeRepositoryImpl(get())
    }
    single<OfferRepository> {
       OfferRepositoryImpl(get(),get())
    }
    single<AnnouncementRepository> {
        AnnouncementRepositoryImpl(get(), get())
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
        TicketRepositoryImpl(get(),get())
    }

    single<WalletRepository> {
        WalletRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userService = get(UserService::class),
            auth = get(),
            userDao = get(UserDao::class)
        )
    }
}
