package com.englizya.charging.utils

object Validator {

    fun isValidAmount(amount: String?): Boolean {
        return amount?.let { isValidDouble(it) } == true
    }

    fun isValidDouble(amount: String): Boolean {
        return amount.matches("^[0-9]{1,10}(\\.[0-9]{1,2})?$".toRegex())
    }

    fun isValidInt(amount: String): Boolean {
        return amount.matches("^[0-9]{1,4}$".toRegex()) == true

    }
}