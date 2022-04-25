package com.englizya.forgetpassword.di

import com.englizya.forgetpassword.ForgetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val forgetPasswordModule = module {

    viewModel{
        ForgetPasswordViewModel()
    }
}