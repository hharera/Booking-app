package com.englyzia.booking.di

import com.englyzia.booking.BookingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookingModule = module {

    viewModel {
        BookingViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}