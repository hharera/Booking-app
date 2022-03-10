package com.englizya.carsocket.request

data class UpdateCarLocationRequest(
    val carCode : Int,
    val carLatitude : Double,
    val carLongitude : Double,
)
