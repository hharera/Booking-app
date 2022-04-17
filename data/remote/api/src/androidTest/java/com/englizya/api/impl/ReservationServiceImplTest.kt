package com.englizya.api.impl

import com.englizya.api.TripService
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.model.request.TripSearchRequest
import io.ktor.util.*
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@InternalAPI
class ReservationServiceImplTest : KoinTest {

    private val tripService: TripService by inject()

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                arrayListOf(
                    clientModule,
                    remoteModule
                )
            )
        }
    }

    @Test
    suspend fun test(): Unit {
        tripService.searchTrips(
            TripSearchRequest(
                DateTime.now().toString(),
                1,
                2
            )
        )
    }
}