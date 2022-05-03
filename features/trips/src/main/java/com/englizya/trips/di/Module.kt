package com.englizya.trips.di

import com.englizya.trips.TripsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tripsModule = module {
    viewModel {
        TripsViewModel(get())
    }
}