package com.englizya.app.ticket

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import java.util.*


@HiltAndroidApp
class TicketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        TimeZone.setDefault(TimeZone.getTimeZone("EET"));
    }

}