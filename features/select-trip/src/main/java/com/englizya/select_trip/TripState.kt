package com.englizya.select_trip

sealed class TripState{
    object Loading : TripState()
    object Success : TripState()
    data class Error(val message: String) : TripState()
}
