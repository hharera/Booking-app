package com.harera.firebase.di

import com.harera.firebase.AuthenticationManager
import com.harera.firebase.impl.FirebaseAuthenticationManager
import org.koin.dsl.module

val firebaseServiceModule = module {

    single<AuthenticationManager> {
        FirebaseAuthenticationManager(get())
    }
}