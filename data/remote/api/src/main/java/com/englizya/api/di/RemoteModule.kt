package com.englizya.api.di

import com.englizya.api.RemoteAnnouncementService
import com.englizya.api.RemoteUserService
import com.englizya.api.impl.RemoteAnnouncementServiceImpl
import com.englizya.api.impl.RemoteUserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Singleton
    @Binds
    abstract fun provideRemoteUserService(remoteUserServiceImpl: RemoteUserServiceImpl): RemoteUserService

    @Singleton
    @Binds
    abstract fun provideAnnouncementsService(announcementService: RemoteAnnouncementServiceImpl): RemoteAnnouncementService
}