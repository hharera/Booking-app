package com.englizya.repository.di

import com.englizya.repository.BookingOfficeRepository
import com.englizya.repository.TripRepository
import com.englizya.repository.UserRepository
import com.englizya.repository.impl.BookingOfficeRepositoryImpl
import com.englizya.repository.impl.TripRepositoryImpl
import com.englizya.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(authenticationManagerImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindTripRepository(tripRepository: TripRepositoryImpl): TripRepository

    @Binds
    @Singleton
    abstract fun bindBookingOfficeRepository(officeRepositoryImpl: BookingOfficeRepositoryImpl): BookingOfficeRepository
}