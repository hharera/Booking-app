package com.englizya.complaint.di

import com.englizya.complaint.ComplaintViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val complaintModule = module {

    viewModel {
        ComplaintViewModel(get(), get())
    }
}