package com.englizya.app.ticket

import android.app.Application
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.car_socket.di.socketModule
import com.englizya.car_socket.di.stompModule
import com.englizya.common.di.baseModule
import com.englizya.datastore.di.dataStoreModule
import com.englizya.feature.set_password.di.setPasswordModule
import com.englizya.local.di.databaseModule
import com.englizya.location_update.di.locationViewModel
import com.englizya.login.di.loginModule
import com.englizya.profile.di.profileModule
import com.englizya.repository.di.repositoryModule
import com.englizya.send_otp.di.sendOtpModule
import com.englizya.signup.di.signupModule
import com.englizya.splash.splashModule
import com.englizya.user_data.di.module
import com.englyzia.booking.di.bookingModule
import com.google.firebase.FirebaseApp
import com.harera.firebase.di.firebaseModule
import com.harera.firebase.di.repositoryModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*
import java.util.logging.Level


class TicketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        TimeZone.setDefault(TimeZone.getTimeZone("EET"));


        setupKoin()
    }

    private fun setupKoin() {
        startKoin() {
//            androidLogger(if (BuildConfig.DEBUG) ERROR else Level.NONE)
            androidContext(applicationContext)
            modules(
                repositoryModule,
                bookingModule,
                databaseModule,
                baseModule,
                clientModule,
                remoteModule,
                firebaseModule,
                repositoryModules,
                socketModule,
                stompModule,
                repositoryModule,
                dataStoreModule,
//                ComplaintModule,
                locationViewModel,
                sendOtpModule,
                setPasswordModule,
                signupModule,
                splashModule,
                module,
                bookingModule,
                profileModule,
                loginModule,
                locationViewModel,
            )
        }
    }

}