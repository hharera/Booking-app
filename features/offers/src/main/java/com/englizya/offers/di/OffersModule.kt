package com.englizya.offers.di

import com.englizya.offers.OffersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val offersModule = module {
    viewModel {
        OffersViewModel(get())
    }
}