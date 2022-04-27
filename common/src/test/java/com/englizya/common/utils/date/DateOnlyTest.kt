package com.englizya.common.utils.date

import com.google.common.truth.Truth
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Test
import java.util.*

internal class DateOnlyTest {

    @Test
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

    //    test date
    @Test
    fun toDate() {
        DateOnly.map("2018-01-01 222").let {
            println(it)

            Truth.assertThat(
                it
            )
        }
    }

}
