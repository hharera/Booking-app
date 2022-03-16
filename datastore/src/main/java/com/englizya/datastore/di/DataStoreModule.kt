package com.englizya.datastore.di

import android.app.Application
import com.englizya.datastore.core.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    companion object {

        @Provides
        @Singleton
        fun provideUserDataStore(application: Application): UserDataStore =
            UserDataStore(context = application)
    }
}