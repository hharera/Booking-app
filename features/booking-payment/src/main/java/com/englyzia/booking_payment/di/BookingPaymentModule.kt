package com.englyzia.booking_payment.di

import com.englyzia.booking_payment.BookingPaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookingPaymentModule = module {

    viewModel {
           BookingPaymentViewModel(get(), get())

    }
}