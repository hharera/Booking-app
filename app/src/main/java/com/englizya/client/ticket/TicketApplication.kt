package com.englizya.client.ticket

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.app_settings.di.settingsModule
import com.englizya.car_socket.di.socketModule
import com.englizya.car_socket.di.stompModule
import com.englizya.charging.di.rechargingModule
import com.englizya.common.di.baseModule
import com.englizya.complaint.di.complaintModule
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.di.dataStoreModule
import com.englizya.feature.set_password.di.setPasswordModule
import com.englizya.feature.ticket.di.ticketDetailsModule
import com.englizya.firebase.di.firebaseModule
import com.englizya.firebase.di.firebaseServiceModule
import com.englizya.forgetpassword.di.forgetPasswordModule
import com.englizya.home_screen.di.homeModule
import com.englizya.local.di.databaseModule
import com.englizya.location_update.di.locationViewModel
import com.englizya.login.di.loginModule
import com.englizya.offers.di.offersModule
import com.englizya.profile.di.profileModule
import com.englizya.repository.di.repositoryModule
import com.englizya.reset_password.di.resetPasswordModule
import com.englizya.route.di.externalRoute
import com.englizya.route.di.internalRoute
import com.englizya.send_otp.di.sendOtpModule
import com.englizya.signup.di.signupModule
import com.englizya.splash.splashModule
import com.englizya.user_data.di.module
import com.englizya.user_tickets.di.userTicketModule
import com.englyzia.booking.di.bookingModule
import com.englyzia.booking_payment.di.bookingPaymentModule
import com.englyzia.reviewdriver.di.driverReviewModule
import com.google.firebase.FirebaseApp
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import org.jasypt.util.text.AES256TextEncryptor
import org.jasypt.util.text.TextEncryptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.*


class TicketApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        TimeZone.setDefault(TimeZone.getTimeZone("EET"));

        setupAppCenter()
        setupKoin()
    }

    private fun setupLanguage() {
        val locale = Locale.Builder().setLanguage(UserDataStore(this).getLanguage()).build()
        Locale.setDefault(locale)
        val resources: Resources = resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        createConfigurationContext(config)
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
                    externalRoute,
                    internalRoute,
                    ticketDetailsModule,
                    offersModule,
                    profileModule,
                    bookingPaymentModule,
                    loginModule,
                    locationViewModel,
                    contextModule,
                    remoteModule,
                    homeModule,
                    driverReviewModule,
                    rechargingModule,
                    settingsModule,
                    module {
                        single<TextEncryptor> {
                            AES256TextEncryptor().apply {
                                setPassword(BuildConfig.SECRET_KEY)
                            }
                        }
                    },
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