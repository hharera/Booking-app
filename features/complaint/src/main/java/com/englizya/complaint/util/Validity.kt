package com.englizya.complaint.util

object Validity {

    fun isDescValid(string: String) =
        string.matches(Regex("^[!@#\$%^&*(),.?\":{}|<>]{10,200}\$"))

    fun isTitleValid(string: String) =
        string.matches(Regex("^[!@#\$%^&*(),.?\":{}|<>]{10,50}\$"))
}