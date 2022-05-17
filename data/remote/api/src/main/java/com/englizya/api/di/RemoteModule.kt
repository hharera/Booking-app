package com.englizya.api.di

import com.englizya.api.*
import com.englizya.api.impl.*
import org.koin.dsl.module


val remoteModule = module {

    single<UserService> {
        UserServiceImpl(get())
    }

    single<AnnouncementService> {
        AnnouncementServiceImpl(get())
    }

    single<ReservationService> {
        ReservationServiceImpl(get())
    }

    single<PaymentService> {
        PaymentServiceImpl(get())
    }

    single<StationService> {
        StationServiceImpl(get())
    }

    single<BookingOfficeService> {
        BookingOfficeServiceImpl(get())
    }

    single<TripService> {
        TripServiceImpl(get())
    }

    single<TicketService> {
        TicketServiceImpl(get())
    }

    single<SupportService> {
        SupportServiceImpl(get())
    }

    single<WalletService> {
        WalletServiceImpl(get())
    }
}