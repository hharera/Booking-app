package com.englizya.route.di

import com.englizya.route.ExternalRouteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val externalRoute = module {

    viewModel {
        ExternalRouteViewModel(get(),get())
    }

}

val internalRoute = module {

    viewModel {
        InternalRouteViewModel()
    }

}