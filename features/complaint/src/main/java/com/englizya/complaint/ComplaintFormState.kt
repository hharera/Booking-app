package com.englizya.complaint

data class ComplaintFormState(
    var titleError: Int? = null,
    var descriptionError: Int? = null,
    var formIsValid: Boolean = false,
)
