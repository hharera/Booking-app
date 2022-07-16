package com.englizya.common.utils.time

import junit.framework.TestCase
import org.junit.Test


internal class TimeOnlyTest{

    @Test
    fun `test to map time to 12 time system`(): Unit {
        TimeOnly.map("2022-06-01T05:50:00.000+00:00").let {
            println("====================")
            println(it)
        }
    }

    @Test
    fun `test to map time to 24 time system`(): Unit {
        TimeOnly.timeIn24TimeSystem("2022-06-29T09:46:43.000+00:00").let {
            println("====================")
            println(it)
        }
    }

    fun testMap() {}

}