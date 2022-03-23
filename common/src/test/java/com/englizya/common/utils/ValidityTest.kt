package com.englizya.common.utils

import junit.framework.TestCase
import org.junit.Test

class ValidityTest : TestCase() {

    @Test
    fun checkPhoneNumberValidity() {
        assertEquals(Validity.phoneNumberIsValid("01062227715"), true )
    }

    @Test
    fun checkEmailValidity() {
        assertEquals(Validity.checkEmail("hassanstar201118@gmail.com"), true )
    }

    @Test
    fun `more than 3 characters should fails`() {
        assertEquals(Validity.cardCvvIsValid("12345"), false )
    }
}