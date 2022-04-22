package com.englizya.complaint.util

object Validity {

    fun isDescValid(string: String) =
        (string.length in 10..500)

    fun isTitleValid(string: String) =
        (string.length in 10..200)
}