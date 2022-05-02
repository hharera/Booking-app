package com.englizya.user_tickets.di

import com.englizya.user_tickets.UserTicketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userTicketModule = module {

    viewModel {
        UserTicketsViewModel(get(), get())
    }

}