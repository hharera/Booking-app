package com.englizya.car_socket.impl

import com.englizya.car_socket.CarSocket
import com.englizya.carsocket.request.CarUpdate
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Flowable
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject

class CarSocketImpl @Inject constructor(
    private val stompClient: StompClient
): CarSocket {

    override fun updateCarLocation(carUpdate: CarUpdate): Completable =
        stompClient.apply { connect() }.send("car/${carUpdate.carCode}", Gson().toJson(carUpdate))

    override fun connectToLineCars(lineCode : Int): Flowable<StompMessage> =
        stompClient.apply { connect() }.topic("line/$lineCode")

    override fun connectToCar(carCode : Int): Flowable<StompMessage> =
        stompClient.apply { connect() }.topic("car/$carCode")
}