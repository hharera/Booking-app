package com.englizya.signup.di

import com.englizya.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signupModule = module {

    viewModel {
        SignupViewModel()
    }
}