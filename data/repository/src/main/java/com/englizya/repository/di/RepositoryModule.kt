package com.englizya.repository.di

import com.englizya.repository.AuthManager
import com.englizya.repository.UserRepository
import com.englizya.repository.impl.FirebaseAuthManager
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
    abstract fun bindAuthenticationManager(authManifestoRepositoryImpl: FirebaseAuthManager) : AuthManager
}