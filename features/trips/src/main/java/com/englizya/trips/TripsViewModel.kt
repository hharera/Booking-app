package com.englizya.trips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Trip
import com.englizya.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val tripsRepository: TripRepository,
) : BaseViewModel() {

    private var _trips = MutableLiveData<List<Trip>>()
    val trips: LiveData<List<Trip>> = _trips

    suspend fun getAlTrips(trips: List<Trip>) {
        updateLoading(true)
        tripsRepository
            .getAllTrips()
            .onSuccess {
                updateLoading(false)
                _trips.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun setTrips(trips: List<Trip>) {
        _trips.value = trips
    }



}
