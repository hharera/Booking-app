package com.englizya.select_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class SelectLocationViewModel : ViewModel() {

    private val _location: MutableLiveData<LatLng> = MutableLiveData<LatLng>()
    val location: LiveData<LatLng> = _location

    fun setLocation(location: LatLng) {
        _location.value = location
    }
}