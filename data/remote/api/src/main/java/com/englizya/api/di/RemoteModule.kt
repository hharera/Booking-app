package com.englizya.api.di

import com.englizya.api.BookingOfficeService
import com.englizya.api.AnnouncementService
import com.englizya.api.UserService
import com.englizya.api.TripService
import com.englizya.api.impl.BookingOfficeServiceImpl
import com.englizya.api.impl.AnnouncementServiceImpl
import com.englizya.api.impl.UserServiceImpl
import com.englizya.api.impl.TripServiceImpl
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
}