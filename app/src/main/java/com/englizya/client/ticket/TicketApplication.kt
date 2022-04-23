package com.englizya.client.ticket

import android.app.Application
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.car_socket.di.socketModule
import com.englizya.car_socket.di.stompModule
import com.englizya.common.di.baseModule
import com.englizya.complaint.di.complaintModule
import com.englizya.datastore.di.dataStoreModule
import com.englizya.feature.set_password.di.setPasswordModule
import com.englizya.home_screen.di.homeModule
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
import com.englyzia.reviewdriver.di.driverReviewModule
import com.google.firebase.FirebaseApp
import com.harera.firebase.di.firebaseModule
import com.harera.firebase.di.firebaseServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.*


class TicketApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        TimeZone.setDefault(TimeZone.getTimeZone("EET"));

        setupKoin()
    }

    private fun setupKoin() {
        startKoin() {
            androidContext(this@TicketApplication)
            modules(
                arrayListOf(
                    firebaseModule,
                    firebaseServiceModule,
                    clientModule,
                    dataStoreModule,
                    databaseModule,
                    baseModule,
                    socketModule,
                    stompModule,
                    repositoryModule,
                    complaintModule,
                    sendOtpModule,
                    setPasswordModule,
                    signupModule,
                    splashModule,
                    module,
                    bookingModule,
                    profileModule,
                    loginModule,
                    locationViewModel,
                    contextModule,
                    remoteModule,
                    homeModule,
                    driverReviewModule,
                )
            )
        }
    }

}

val contextModule = module {

    single<FirebaseApp> {
        FirebaseApp.initializeApp(get())!!
    }
}