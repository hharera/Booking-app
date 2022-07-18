package com.englizya.profile_settings.di

import com.englizya.profile_settings.ProfileSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val profileSettingsModule = module {

    viewModel {
        ProfileSettingsViewModel(
            get(),
            get(),

            )
    }
}