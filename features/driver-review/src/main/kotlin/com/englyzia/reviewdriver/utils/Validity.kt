package com.englyzia.reviewdriver.utils

object Validity {

    fun isReviewValid(review: Float?): Boolean =
        review.toString().matches(Regex("^[0-5].[0-99]\$"))

    fun isDriverCodeValid(driverCode: String?): Boolean {
        if (driverCode.isNullOrBlank())
            return false

        return driverCode.matches(Regex("^[0-9]{1,20}\$"))
    }
}