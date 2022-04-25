package com.englizya.reset_password.di

import com.englizya.reset_password.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resetPasswordModule = module {

    viewModel {
        ResetPasswordViewModel(get())
    }
}