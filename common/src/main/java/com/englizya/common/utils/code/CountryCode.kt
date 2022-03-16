package com.englizya.common.utils.code

sealed class CountryCode(val code : String) {

    object EgyptianCode : CountryCode("+2")
}