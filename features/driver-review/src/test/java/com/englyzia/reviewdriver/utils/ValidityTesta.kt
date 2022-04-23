package com.englyzia.reviewdriver.utils

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

import junit.framework.TestCase

class ValidityTesta : TestCase() {

    @Test
    fun `string with spaces should be blank`() {
        Truth.assertThat(
            " ".isBlank()
        ).isEqualTo(true)
    }

    @Test
    fun `string with spaces should not be empty`() {
        Truth.assertThat(
            " ".isEmpty()
        ).isEqualTo(false)
    }

    @Test
    fun `null string with should be is nullOrBlank`() {
        Truth.assertThat(
            (null).isNullOrBlank()
        ).isEqualTo(true)
    }

    @Test
    fun `driver code with characters should fails`() {
        Truth.assertThat(
            Validity.isDriverCodeValid("ff")
        ).isEqualTo(false)
    }

    @Test
    fun `driver code with less than digit should fails`() {
        Truth.assertThat(
            Validity.isDriverCodeValid(" ")
        ).isEqualTo(false)
    }

    @Test
    fun `driver code with more than 20 digit should fails`() {
        Truth.assertThat(
            Validity.isDriverCodeValid("123456789123456789122")
        ).isEqualTo(false)
    }

    @Test
    fun `driver code with more normal code should success`() {
        Truth.assertThat(
            Validity.isDriverCodeValid("123")
        ).isEqualTo(true)
    }

    @Test
    fun `review with more than 5 should fail`() {
        Truth.assertThat(
            Validity.isReviewValid(5.16f)
        ).isEqualTo(false)
    }

}