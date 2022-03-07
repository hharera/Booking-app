package com.englizya.car_socket.di

import com.englizya.car_socket.CarSocket
import com.englizya.car_socket.impl.CarSocketImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.http.auth.*
import ua.naiksoftware.stomp.StompClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SocketModule {

    companion object {

        @Provides
        @Singleton
        fun provideCarSocket(stompClient: StompClient): CarSocket = CarSocketImpl(stompClient)
    }
}