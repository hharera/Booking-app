package com.englizya.join_us.di

import com.englizya.join_us.JobsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val jobsModule = module {

    viewModel {
        JobsViewModel(

        )
    }
}