package com.englyzia.reviewdriver

data class DriverReviewForm(
    var driverCodeError: Int? = null,
    var reviewMessageError: Int? = null,
    var reviewError: Int? = null,
    var formIsValid: Boolean = false,
)
