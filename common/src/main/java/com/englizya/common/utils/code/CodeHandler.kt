package com.englizya.common.utils.code

object CodeHandler {


    fun appendCode(phoneNumber : String, countryCode : CountryCode) = countryCode.code.plus(phoneNumber)
}