package com.englizya.profile.di

import com.englizya.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel {
        ProfileViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}