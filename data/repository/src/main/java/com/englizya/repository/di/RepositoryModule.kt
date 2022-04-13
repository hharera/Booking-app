package com.englizya.repository.di

import com.englizya.repository.*
import com.englizya.repository.impl.*
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

    @Binds
    @Singleton
    abstract fun bindStationRepository(stationRepositoryImpl: StationRepositoryImpl): StationRepository

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository
}