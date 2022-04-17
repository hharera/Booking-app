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

    single<StationRepository> {
        StationRepositoryImpl(get())
    }

    single<PaymentRepository> {
        PaymentRepositoryImpl(get())
    }

    single<ReservationRepository> {
        ReservationRepositoryImpl(get())
    }

    single<ComplaintRepository> {
        ComplaintRepositoryImpl()
    }
}
