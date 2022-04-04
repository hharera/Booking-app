package com.englizya.common.mapper

import junit.framework.TestCase
import org.joda.time.DateTime

class DateTimeMapperTest : TestCase() {

    fun testMap() {
        val millis = DateTime.now().millis
        DateTimeMapper.map(millis / 1000).let {
            println(it)
        }
    }
}