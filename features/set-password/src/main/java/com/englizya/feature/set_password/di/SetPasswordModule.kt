package com.englizya.feature.set_password.di

import com.englizya.feature.set_password.SetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val setPasswordModule = module {
    viewModel {
        SetPasswordViewModel(get(), get())
    }
}