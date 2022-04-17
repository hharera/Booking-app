package com.englizya.car_socket.di

import com.englizya.car_socket.utils.Routing.ENGLIZYA_WEB_SOCKET
import org.koin.dsl.module
import ua.naiksoftware.stomp.Stomp.ConnectionProvider
import ua.naiksoftware.stomp.Stomp.over


val stompModule = module {

    single {
        over(ConnectionProvider.OKHTTP, ENGLIZYA_WEB_SOCKET)
    }
}