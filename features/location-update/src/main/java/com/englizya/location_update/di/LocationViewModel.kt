package com.englizya.location_update.di

import com.englizya.location_update.LocationUpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationViewModel = module {

    viewModel {
        LocationUpdateViewModel(get())
    }
}