package com.englizya.car_socket.di

import com.englizya.car_socket.utils.Routing.ENGLIZYA_WEB_SOCKET
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.Stomp.*
import ua.naiksoftware.stomp.StompClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {

    @Provides
    @Singleton
    fun provideCarStompConnection (): StompClient = over(ConnectionProvider.OKHTTP, ENGLIZYA_WEB_SOCKET)
}