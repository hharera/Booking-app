package com.englyzia.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Station
import com.englizya.model.model.ReservationSeat
import com.englizya.model.model.Trip
import com.englizya.model.request.TripSearchRequest
import com.englizya.repository.StationRepository
import com.englizya.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val stationRepository: StationRepository,
    private val tripsRepository: TripRepository,
) : BaseViewModel() {

    private var _formValidity = MutableLiveData<BookingFormState>()
    val formValidity: LiveData<BookingFormState> = _formValidity

    private var _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    private var _source = MutableLiveData<Station>()
    val source: LiveData<Station> = _source

    private var _destination = MutableLiveData<Station>()
    val destination: LiveData<Station> = _destination

    private var _date = MutableLiveData<DateTime>()
    val date: LiveData<DateTime> = _date

    private var _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>> = _stations

    private var _trips = MutableLiveData<List<Trip>>()
    val trips: LiveData<List<Trip>> = _trips

    private var _trip = MutableLiveData<Trip>()
    val trip: LiveData<Trip> = _trip

    private var _selectedSeats = MutableLiveData<Set<ReservationSeat>>(emptySet())
    val selectedSeats: LiveData<Set<ReservationSeat>> = _selectedSeats

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


    suspend fun getBookingOffices() {
        updateLoading(true)
        stationRepository
            .getAllStations()
            .onSuccess {
                updateLoading(false)
                _stations.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun checkFormValidity() {
        if (null == _source.value) {
            _formValidity.postValue(BookingFormState(sourceError = R.string.source_is_required))
        } else if (null == _destination.value) {
            _formValidity.postValue(BookingFormState(destinationError = R.string.destincation_is_required))
        } else if (null == _date.value) {
            _formValidity.postValue(BookingFormState(dateError = R.string.date_is_required))
        } else {
            _formValidity.postValue(BookingFormState(formIsValid = true))
        }
    }


    fun setDestination(destination: String) {
        _destination.value = stations.value?.firstOrNull {
            it.branchName == destination
        }
        checkFormValidity()
    }

    fun setSource(source: String) {
        Log.d(TAG, "setSource: $source")
        _source.value = stations.value?.firstOrNull {
            it.branchName == source
        }
        checkFormValidity()
    }

    fun setDate(date: DateTime) {
        Log.d(TAG, "setDate: $date")
        _date.value = date
        checkFormValidity()
    }

    suspend fun searchTrips() {
        updateLoading(true)
        tripsRepository.searchTrips(
            TripSearchRequest(
                date = date.value!!.toString(),
                sourceStationId = source.value!!.branchId,
                destinationStationId = destination.value!!.branchId
            )
        ).onSuccess {
            updateLoading(false)
            _trips.postValue(it)
        }.onFailure {
            updateLoading(false)
            handleException(it)
        }
    }

    fun setSelectedTrip(trip: Trip) {
        _trip.value = trip
    }

    fun setSelectedSeat(seat: ReservationSeat) {
        if (selectedSeats.value?.contains(seat) == true) {
            _selectedSeats.value = selectedSeats.value?.minus(seat)
        } else {
            _selectedSeats.value = selectedSeats.value?.plus(seat)
        }

        _total.value = trip.value?.plan?.seatPrices?.firstOrNull {
            it.source == source.value?.branchId && it.destination == destination.value?.branchId
        }?.vipPrice?.let {
            selectedSeats.value?.size?.times(
                it
            )
        }
    }
}
