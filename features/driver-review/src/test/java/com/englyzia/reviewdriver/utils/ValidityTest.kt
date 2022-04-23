package com.englyzia.reviewdriver.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class ValidityTest {

    @Test
    fun `string with spaces should be blank`() {
        assertThat(
            " ".isBlank()
        ).isEqualTo(true)
    }

    @Test
    fun `string with spaces should not be empty`() {
        assertThat(
            " ".isEmpty()
        ).isEqualTo(false)
    }

    @Test
    fun `null string with should be is nullOrBlank`() {
        assertThat(
           (null).isNullOrBlank()
        ).isEqualTo(true)
    }

    @Test
    fun `driver code with characters should fails`() {
        assertThat(
            Validity.isDriverCodeValid("ff")
        ).isEqualTo(false)
    }

    @Test
    fun `driver code with less than digit should fails`() {
        assertThat(
            Validity.isDriverCodeValid(" ")
        ).isEqualTo(false)
    }

    @Test
    fun `driver code with more than 20 digit should fails`() {
        assertThat(
            Validity.isDriverCodeValid("123456789123456789122")
        ).isEqualTo(false)
    }

    @Test
    fun `driver code with more normal code should success`() {
        assertThat(
            Validity.isDriverCodeValid("123")
        ).isEqualTo(true)
    }

    @Test
    fun `review with more than 5 should fail`() {
        assertThat(
            Validity.isReviewValid(5.16f)
        ).isEqualTo(false)
    }
}