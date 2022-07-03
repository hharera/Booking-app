package com.englizya.route.di

import com.englizya.route.RouteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val externalRoute = module {

    viewModel {
        RouteViewModel(get()
//            ,get(),get()
        )
    }

}

