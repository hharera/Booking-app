package com.englyzia.booking

data class BookingFormState(
    var sourceError: Int? = null,
    var destinationError: Int? = null,
    var dateError: Int? = null,
    var formIsValid: Boolean = false,
)
