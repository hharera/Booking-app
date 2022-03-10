package com.englizya.car_socket

import com.englizya.carsocket.request.UpdateCarLocationRequest
import io.reactivex.Completable
import io.reactivex.Flowable
import ua.naiksoftware.stomp.dto.StompMessage


interface CarSocket {

    fun updateCarLocation (updateCarLocationRequest: UpdateCarLocationRequest): Completable
    fun connectToLineCars(lineCode: Int): Flowable<StompMessage>
    fun connectToCar(carCode: Int): Flowable<StompMessage>
}