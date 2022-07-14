package com.englizya.profile_settings.di

import com.englizya.profile_settings.CoilLoader
import org.koin.dsl.module

val coilModule = module {

    single {
        CoilLoader(get(), get())
    }
}