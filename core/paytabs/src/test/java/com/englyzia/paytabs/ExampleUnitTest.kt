package com.englyzia.paytabs

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test cart description string`() {
        val seatsCount = 5
        val tripName = "رحلة "
        println("حجز $seatsCount مقعد/مثاعد علي $tripName")
    }
}