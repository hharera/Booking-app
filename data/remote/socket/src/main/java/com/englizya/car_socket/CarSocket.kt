package com.englizya.car_socket

import com.englizya.carsocket.request.CarUpdate
import io.reactivex.Completable
import io.reactivex.Flowable
import ua.naiksoftware.stomp.dto.StompMessage


interface CarSocket {

    fun updateCarLocation (carUpdate: CarUpdate): Completable
    fun connectToLineCars(lineCode: Int): Flowable<StompMessage>
    fun connectToCar(carCode: Int): Flowable<StompMessage>
}