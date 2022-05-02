package com.englizya.firebase.di

import com.englizya.firebase.AuthenticationManager
import com.englizya.firebase.impl.FirebaseAuthenticationManager
import org.koin.dsl.module

val firebaseServiceModule = module {

    single<AuthenticationManager> {
        FirebaseAuthenticationManager(get())
    }
}