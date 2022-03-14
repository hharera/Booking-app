package com.harera.firebase.di

import com.harera.firebase.*
import com.harera.firebase.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Binds
    abstract fun bindAuthManager(manager: FirebaseAuthenticationManager): AuthenticationManager
}