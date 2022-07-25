package com.englizya.internal_search.di

import com.englizya.internal_search.InternalSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val internalSearchModule = module {
    viewModel {
        InternalSearchViewModel(get())
    }
}