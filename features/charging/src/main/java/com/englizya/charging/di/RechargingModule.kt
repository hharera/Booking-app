package com.englizya.charging.di

import com.englizya.charging.RechargingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val rechargingModule = module {

    viewModel {
        RechargingViewModel(
            get(),
            get(),
            get(),
            get(),
        )
    }
}