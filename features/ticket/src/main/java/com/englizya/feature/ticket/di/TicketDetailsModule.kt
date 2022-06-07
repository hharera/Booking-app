package com.englizya.feature.ticket.di

import com.englizya.feature.ticket.TicketDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ticketDetailsModule = module {

    viewModel {
       TicketDetailsViewModel(get(), get())
    }

}