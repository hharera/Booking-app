package com.englizya.location_update

import com.englizya.car_socket.CarSocket
import com.englizya.carsocket.request.CarUpdate
import com.englizya.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class LocationUpdateViewModel @Inject constructor(
    private val carSocket: CarSocket
) : BaseViewModel() {

    fun sendRandomLocation() {
        carSocket.updateCarLocation(
            CarUpdate(
                carCode = Random.nextInt(9) + 10,
                carLatitude = Random.nextDouble(20.0) + 10.0,
                carLongitude = Random.nextDouble(20.0) + 10.0
            )
        )
    }
}