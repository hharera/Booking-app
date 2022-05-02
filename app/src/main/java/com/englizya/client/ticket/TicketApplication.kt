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
import com.englizya.forgetpassword.di.forgetPasswordModule
import com.englizya.home_screen.di.homeModule
import com.englizya.local.di.databaseModule
import com.englizya.location_update.di.locationViewModel
import com.englizya.login.di.loginModule
import com.englizya.profile.di.profileModule
import com.englizya.repository.di.repositoryModule
import com.englizya.reset_password.di.resetPasswordModule
import com.englizya.send_otp.di.sendOtpModule
import com.englizya.signup.di.signupModule
import com.englizya.splash.splashModule
import com.englizya.user_data.di.module
import com.englyzia.booking.di.bookingModule
import com.englyzia.reviewdriver.di.driverReviewModule
import com.google.firebase.FirebaseApp
import com.englizya.firebase.di.firebaseModule
import com.englizya.firebase.di.firebaseServiceModule
import com.englizya.user_tickets.di.userTicketModule
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
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
        setupAppCenter()
    }

    private fun setupAppCenter() {
        AppCenter.start(
            this,
            "3e587360-616d-4d54-b801-0bacab2ecc54",
            Analytics::class.java,
            Crashes::class.java
        )
    }

    private fun setupKoin() {
        startKoin() {
            androidContext(this@TicketApplication)
            modules(
                arrayListOf(
                    firebaseModule,
                    firebaseServiceModule,
                    clientModule,
                    forgetPasswordModule,
                    resetPasswordModule,
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
                    userTicketModule,
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