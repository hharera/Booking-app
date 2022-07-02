package com.englizya.announcement.di

import com.englizya.announcement.AnnouncementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val announcementModule = module {
    viewModel {
        AnnouncementViewModel(get(),get())
    }
}