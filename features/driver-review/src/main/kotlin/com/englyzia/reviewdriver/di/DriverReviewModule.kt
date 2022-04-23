package com.englyzia.reviewdriver.di

import com.englyzia.reviewdriver.DriverReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val driverReviewModule = module {

    viewModel {
        DriverReviewViewModel(get(), get())
    }
}