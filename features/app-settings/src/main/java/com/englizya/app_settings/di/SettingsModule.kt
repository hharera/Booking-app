package com.englizya.app_settings.di

import com.englizya.app_settings.SettingsViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel {
        SettingsViewModel(get())
    }
}