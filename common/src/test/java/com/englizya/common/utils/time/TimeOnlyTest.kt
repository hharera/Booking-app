package com.englizya.common.utils.time

import junit.framework.TestCase
import org.junit.Test


internal class TimeOnlyTest{

    @Test
    fun `test if time formatted successfully`(): Unit {
        TimeOnly.map("2022-04-01 18:00:00").let {
            println("====================")
            println(it)
        }
    }

    fun testMap() {}

}