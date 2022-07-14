package com.englizya.model.request

import java.io.File

data class UserEditRequest(
    val name: String,
    val address: String,
    val image: File,

    )
