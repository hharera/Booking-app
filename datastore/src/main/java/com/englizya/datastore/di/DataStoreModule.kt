package com.englizya.datastore.di

import com.englizya.datastore.UserDataStore
import org.koin.dsl.module


val dataStoreModule = module {

    single {
        UserDataStore(get())
    }
}