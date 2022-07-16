package com.englizya.charging.utils

import org.junit.Assert.*

class ValidatorTest {
    @org.junit.Test
    fun isValidEmail() {
        assertTrue(Validator.isValidAmount("55"))
    }
}