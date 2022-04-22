package com.englizya.common.utils.date

import android.util.Log
import com.google.common.truth.Truth
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class DateOnlyTest {

    @org.junit.jupiter.api.Test
    fun map() {
        println(DateTime.now().toString())
        DateOnly.map(
            DateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT")))
        ).let {
            println("$it")
            Truth.assertThat(
                it.seconds
            ).isEqualTo(0)
        }
    }
}