package com.englizya.car_socket.di

import com.englizya.car_socket.utils.Origin.ENGLIZYA_WEB_SOCKET
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.naiksoftware.stomp.Stomp
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {

    @Provides
    @Singleton
    fun provideCarStompConnection () = Stomp.over(Stomp.ConnectionProvider.OKHTTP, ENGLIZYA_WEB_SOCKET);
}