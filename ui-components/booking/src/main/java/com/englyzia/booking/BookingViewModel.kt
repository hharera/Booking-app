package com.englyzia.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.*
import com.englizya.model.request.PaymentRequest
import com.englizya.model.request.TripSearchRequest
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class BookingViewModel constructor(
    private val stationRepository: StationRepository,
    private val tripsRepository: TripRepository,
    private val userRepository: UserRepository,
    private val dataStore: UserDataStore,
    private val reservationRepository: ReservationRepository,
    private val paymentRepository: PaymentRepository,
) : BaseViewModel() {

    companion object {
        const val ACCEPT_PAYMENT_REQUEST = 3005
    }

    private var _paymentAction = MutableStateFlow<Boolean>(false)
    val paymentAction: StateFlow<Boolean> get() = _paymentAction

    private var _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private var _paymentToken = MutableStateFlow<PayMobPaymentResponse?>(null)
    val paymentToken: StateFlow<PayMobPaymentResponse?> get() = this._paymentToken

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

    private var _selectedSeats = MutableLiveData<Set<Seat>>(emptySet())
    val selectedSeats: LiveData<Set<Seat>> = _selectedSeats

    private var _paymentRequest = MutableLiveData<PaymentRequest>()
    val paymentRequest: LiveData<PaymentRequest> = _paymentRequest

    private var _reservationTickets = MutableLiveData<List<ReservationTicket>>()
    val reservationTickets: LiveData<List<ReservationTicket>> = _reservationTickets

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUser()
        }
    }

    private suspend fun fetchUser() {
        updateLoading(true)
        userRepository
            .fetchUser(dataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _user.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

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

    fun setSelectedSeat(seat: Seat) {
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

    fun book() {
        updateLoading(true)

        if (selectedSeats.value!!.isEmpty()) {
            updateLoading(false)
            handleException(R.string.empty_seats_error)
        } else {
            requestPayment()
        }
    }

    private fun requestPayment() {
        updateLoading(true)
        createPaymentRequest()
            .onSuccess {
                updateLoading(false)
                requestPayment(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun requestPayment(request: PaymentRequest) {
        updateLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository
                .requestPayment(request, dataStore.getToken())
                .onSuccess {
                    Log.d(TAG, "requestPayment: $it")
                    updateLoading(false)
                    _paymentToken.value = it
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }
    }

    private fun createPaymentRequest(): Result<PaymentRequest> = kotlin.runCatching {
        PaymentRequest(
            seats = selectedSeats.value!!.toList(),
            source = source.value!!.branchId,
            destination = destination.value!!.branchId,
            date = date.value!!.toString(),
            reservationId = trip.value!!.reservation?.first()?.id.toString(),
            itemPrice = trip.value!!.plan!!.seatPrices.first {
                it.source == source.value!!.branchId
                        && it.destination == destination.value!!.branchId
                        && it.destination == destination.value!!.branchId
            }.vipPrice!!,
            passenger = user.value!!.name,
            phoneMobile = user.value!!.phoneNumber,
            qty = selectedSeats.value!!.size,
            tripId = trip.value!!.tripId.toString(),
            tripName = trip.value!!.tripName.toString(),
        ).also {
            _paymentRequest.value = it
        }
    }

    fun clearSelectSeats() {
        _selectedSeats.value = emptySet()
        _total.value = 0
    }

    suspend fun submitBooking() {
        updateLoading(true)

        paymentRequest.value?.let {
            reservationRepository
                .reserveSeats(it, dataStore.getToken())
                .onSuccess {
                    updateLoading(false)
                    _reservationTickets.postValue(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }
    }

}
