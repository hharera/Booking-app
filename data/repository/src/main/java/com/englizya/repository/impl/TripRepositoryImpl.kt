package com.englizya.repository.impl

import android.util.Log
import com.englizya.api.TripService
import com.englizya.local.Trip.TripDao
import com.englizya.model.model.Trip
import com.englizya.model.request.TripSearchRequest
import com.englizya.repository.TripRepository


class TripRepositoryImpl constructor(
    private val tripService: TripService,
    private val tripDao: TripDao
) : TripRepository {

    override suspend fun getAllTrips(): Result<List<Trip>> =
        kotlin.runCatching {
            tripService.getAllTrips()
        }

    override suspend fun searchTrips(
        request: TripSearchRequest,
        forceOnline: Boolean
    ): Result<List<Trip>> =
        kotlin.runCatching {
            if (forceOnline) {
                tripService.searchTrips(request)
//                    .also {
//                    tripDao.insertTrips(it)
//                    Log.d("TripData", "Remotely")
//                }

            } else {
                tripDao.getTrips().also {
                    Log.d("TripData", "Locally")

                }

            }


        }
}