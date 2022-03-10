package com.englizya.car_socket.impl

import com.englizya.car_socket.CarSocket
import com.englizya.carsocket.request.UpdateCarLocationRequest
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Flowable
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject

class CarSocketImpl @Inject constructor(
    private val stompClient: StompClient
): CarSocket {

    override fun updateCarLocation(updateCarLocationRequest: UpdateCarLocationRequest): Completable =
        stompClient.apply { connect() }.send("car/${updateCarLocationRequest.carCode}", Gson().toJson(updateCarLocationRequest))

    override fun connectToLineCars(lineCode : Int): Flowable<StompMessage> =
        stompClient.apply { connect() }.topic("line/$lineCode")

    override fun connectToCar(carCode : Int): Flowable<StompMessage> =
        stompClient.apply { connect() }.topic("car/$carCode")
}