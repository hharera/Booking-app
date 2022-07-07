package com.englizya.client.ticket

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.os.Build
import com.englizya.announcement.di.announcementModule
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
import com.englizya.datastore.utils.Language
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
        setupAppCenter()
        setupKoin()
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(updateBaseContextLocal(base))
    }

    private fun updateBaseContextLocal(base: Context?): Context? {
        var language = UserDataStore(base!!).getLanguage()//it return "en", "ar" like this
        if (language == null || language.isEmpty()) {
            //when first time enter into app (get the device language and set it
            language = Locale.getDefault().language
            if (language.equals("ar")) {
                UserDataStore(this).setLanguage(Language.Arabic)
            } else if (language.equals("en")) {
                UserDataStore(this).setLanguage(Language.English)
            }
        }
        val locale = language?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(base, locale!!)
            return updateResourcesLocaleLegacy(base, locale)
        }

        return updateResourcesLocaleLegacy(base, locale!!)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context? {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context? {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }


    private fun setupAppCenter() {
        AppCenter.start(
            this,
            BuildConfig.APP_CENTER,
            Analytics::class.java,
            Crashes::class.java
        )
    }

    private fun setupKoin() {
        startKoin {
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
                    ticketDetailsModule,
                    offersModule,
                    announcementModule,
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