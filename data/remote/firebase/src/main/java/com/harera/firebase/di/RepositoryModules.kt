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

    @Binds
    abstract fun bindCartRepository(repository: FirebaseCartRepository): CartRepository

    @Binds
    abstract fun bindSearchRepository(repository: FirebaseSearchRepository): SearchRepository

    @Binds
    abstract fun bindCategoryRepository(repository: FirebaseCategoryRepository): CategoryRepository

    @Binds
    abstract fun bindOfferRepository(repository: FirebaseOfferRepository): OfferRepository

    @Binds
    abstract fun bindWishListRepository(repository: FirebaseWishListRepository): WishListRepository

    @Binds
    abstract fun bindUserRepository(repository: FirebaseUserRepository): UserRepository

    @Binds
    abstract fun bindProductRepository(repository: FirebaseProductRepository): ProductRepository

}