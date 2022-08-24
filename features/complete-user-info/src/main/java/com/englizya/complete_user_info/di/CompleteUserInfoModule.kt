package com.englizya.complete_user_info.di

import com.englizya.complete_user_info.CompleteUserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val completeUserInfoModule = module {
    viewModel{
        CompleteUserInfoViewModel(get(),get(),get())
    }
}