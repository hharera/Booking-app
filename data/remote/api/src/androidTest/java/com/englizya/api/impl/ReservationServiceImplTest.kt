package com.englizya.api.impl

import com.englizya.api.ReservationService
import com.englizya.api.TripService
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.di.dataStoreModule
import com.englizya.model.request.ReservationRequest
import com.englizya.model.request.TripSearchRequest
import io.ktor.util.*
import kotlinx.coroutines.test.runTest
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
    private val reservationService: ReservationService by inject()
    private val userDataStore: UserDataStore by inject()

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                arrayListOf(
                    clientModule,
                    remoteModule,
                    dataStoreModule,
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

    //   test request reservation
    @Test
    fun testRequestReservation() {
        runTest {
            reservationService.requestReservation(
                ReservationRequest(
                    tripId = 1,
                    pathType = 1,
                    reservationId = 1,
                    destinationBranchId = 2,
                    sourceBranchId = 1,
                    seats = setOf(
                        1,
                        2
                    )
                ),
                "test"
            )
        }
    }

}