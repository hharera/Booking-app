package com.englizya.location_update

import com.englizya.car_socket.CarSocket
import com.englizya.model.request.UpdateCarLocationRequest
import com.englizya.common.base.BaseViewModel
import kotlin.random.Random


class LocationUpdateViewModel constructor(
    private val carSocket: CarSocket
) : BaseViewModel() {

    fun sendRandomLocation() {
        carSocket.updateCarLocation(
            UpdateCarLocationRequest(
                carCode = Random.nextInt(9) + 10,
                carLatitude = Random.nextDouble(20.0) + 10.0,
                carLongitude = Random.nextDouble(20.0) + 10.0
            )
        )
    }
}