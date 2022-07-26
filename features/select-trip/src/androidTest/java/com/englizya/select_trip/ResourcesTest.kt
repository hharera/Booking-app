package com.englizya.select_trip

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class ResourcesTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val conf = appContext.resources.configuration
        conf.locale = Locale("ar")
        val metrics = DisplayMetrics()
        val resources = Resources(appContext.assets, metrics, conf)
        resources.getString(R.string.available_seats, "55").also {
            println(it)
            assertEquals("متاح 55", it)
        }
    }
}