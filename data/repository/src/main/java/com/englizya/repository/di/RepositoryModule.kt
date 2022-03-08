package com.englizya.repository.di

import com.englizya.repository.ManifestoRepository
import com.englizya.repository.TicketRepository
import com.englizya.repository.UserRepository
import com.englizya.repository.impl.FirebaseAuthManager
import com.englizya.repository.impl.ManifestoRepositoryImpl
import com.englizya.repository.impl.TicketRepositoryImpl
import com.englizya.repository.impl.UserRepositoryImpl
import com.harera.firebase.AuthenticationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Binds
    abstract fun bindAuthenticationManager(authManifestoRepositoryImpl: FirebaseAuthManager) : UserRepository

    @Binds
    abstract fun bindManifestoRepository(manifestoRepositoryImpl: ManifestoRepositoryImpl) : ManifestoRepository

    @Binds
    abstract fun bindTicketRepository(ticketRepositoryImpl: TicketRepositoryImpl) : TicketRepository
}