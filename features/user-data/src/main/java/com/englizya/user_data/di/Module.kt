package com.englizya.user_data.di

import com.englizya.user_data.UserFormViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    viewModel {
        UserFormViewModel(
            get()
        )
    }
}