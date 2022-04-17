package com.englizya.car_socket.di

import com.englizya.car_socket.impl.CarSocketImpl
import org.koin.dsl.module


val socketModule = module {

    single {
        CarSocketImpl(get())
    }
}