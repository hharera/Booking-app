package com.englizya.send_otp.di

import com.englizya.send_otp.SendOtpViewModel
import org.koin.dsl.module

val sendOtpModule = module {

    single {
        SendOtpViewModel(get())
    }
}