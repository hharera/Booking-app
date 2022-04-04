package com.englizya.api.di

import com.englizya.api.*
import com.englizya.api.impl.*
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
    abstract fun bindUserService(remoteUserServiceImpl: UserServiceImpl): UserService

    @Singleton
    @Binds
    abstract fun bindAnnouncementService(announcementService: AnnouncementServiceImpl): AnnouncementService

    @Singleton
    @Binds
    abstract fun bindTripService(tripServiceImpl: TripServiceImpl): TripService

    @Singleton
    @Binds
    abstract fun bindBookingOfficeService(bookingOfficeService: BookingOfficeServiceImpl): BookingOfficeService

    @Singleton
    @Binds
    abstract fun bindStationService(stationService: StationServiceImpl): StationService
}