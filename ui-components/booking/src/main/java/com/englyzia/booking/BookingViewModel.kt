package com.englyzia.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.date.DateOnly
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.*
import com.englizya.model.request.PaymentRequest
import com.englizya.model.request.ReservationConfirmationRequest
import com.englizya.model.request.ReservationRequest
import com.englizya.model.request.TripSearchRequest
import com.englizya.model.response.OnlineTicket
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.ReservationOrder
import com.englizya.repository.*
import com.englyzia.booking.utils.PaymentMethod
import com.englyzia.paytabs.PayTabsService
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

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

    /**
     * Booking Screen Variables
     */

    private var _source = MutableLiveData<Station>()
    val source: LiveData<Station> = _source

    private var _destination = MutableLiveData<Station>()
    val destination: LiveData<Station> = _destination

    private var _date = MutableLiveData<DateTime>()
    val date: LiveData<DateTime> = _date

    private var _formValidity = MutableLiveData<BookingFormState>()
    val formValidity: LiveData<BookingFormState> = _formValidity

    /**
     * Trips Result Screen
     */

    private var _trips = MutableLiveData<List<Trip>>()
    val trips: LiveData<List<Trip>> = _trips

    private var _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip> = _selectedTrip

    /**
     * Reservation Data
     */

    private var _reservationOrder = MutableStateFlow<ReservationOrder?>(null)
    val reservationOrder: StateFlow<ReservationOrder?> get() = _reservationOrder

    private var _paymentAction = MutableStateFlow<Boolean>(false)
    val paymentAction: StateFlow<Boolean> get() = _paymentAction

    private var _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private var _paymentToken = MutableStateFlow<PayMobPaymentResponse?>(null)
    val paymentToken: StateFlow<PayMobPaymentResponse?> get() = this._paymentToken

    private var _billingDetails = MutableStateFlow<PaymentSdkConfigurationDetails?>(null)
    val billingDetails: StateFlow<PaymentSdkConfigurationDetails?> get() = this._billingDetails

    private var _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    private var _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>> = _stations

    private var _selectedSeats = MutableLiveData<Set<Seat>>(emptySet())
    val selectedSeats: LiveData<Set<Seat>> = _selectedSeats

    private var _paymentRequest = MutableLiveData<PaymentRequest>()
    val paymentRequest: LiveData<PaymentRequest> = _paymentRequest

    private var _reservationTickets = MutableLiveData<List<ReservationTicket>>()
    val reservationTickets: LiveData<List<ReservationTicket>> = _reservationTickets

    private var _onlineTickets = MutableLiveData<List<OnlineTicket>>()
    val onlineTickets: LiveData<List<OnlineTicket>> = _onlineTickets

    private var _transactionRef = MutableLiveData<String?>()
    val transactionRef: LiveData<String?> = _transactionRef

    private var _bookingOffice = MutableLiveData<LineStationTime?>()
    val bookingOffice: LiveData<LineStationTime?> = _bookingOffice

    private var _selectedPaymentMethod = MutableLiveData<PaymentMethod>()
    val selectedPaymentMethod: LiveData<PaymentMethod> = _selectedPaymentMethod

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUser()
        }

        setDefaultDate()
    }

    private fun setDefaultDate() {
        DateOnly.toMonthDate(
            DateTime(DateTimeZone.UTC)
        ).let {
            _date.postValue(DateTime(it))
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

    private fun updateData(list: List<Station>) {
        _source.postValue(list.firstOrNull())
        _destination.postValue(list.lastOrNull())
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
        _selectedTrip.value = trip
        updateBookingOffice(trip)
    }

    private fun updateBookingOffice(trip: Trip) {
        _bookingOffice.value = trip.tripTimes.firstOrNull()
    }

    fun setSelectedSeat(seat: Seat) {
        if (selectedSeats.value?.contains(seat) == true) {
            _selectedSeats.value = selectedSeats.value?.minus(seat)
        } else {
            _selectedSeats.value = selectedSeats.value?.plus(seat)
        }

        _total.value = selectedTrip.value?.plan?.seatPrices?.firstOrNull {
            it.source == source.value?.branchId && it.destination == destination.value?.branchId
        }?.vipPrice?.let {
            selectedSeats.value?.size?.times(
                it
            )
        }
    }

    suspend fun requestReservation() {
        updateLoading(true)

        encapsulateReservationRequest()
            .onSuccess {
                updateLoading(false)
                requestReservation(it)
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private suspend fun requestReservation(reservationRequest: ReservationRequest) {
        updateLoading(true)
        reservationRepository
            .requestReservation(reservationRequest, dataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _reservationOrder.value = it
            }.onFailure {
                updateLoading(false)
                handleException(it)
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
            reservationId = selectedTrip.value!!.reservations.first().id.toString(),
            itemPrice = selectedTrip.value!!.plan!!.seatPrices.first {
                it.source == source.value!!.branchId
                        && it.destination == destination.value!!.branchId
                        && it.destination == destination.value!!.branchId
            }.vipPrice!!,
            passenger = user.value!!.name,
            phoneMobile = user.value!!.phoneNumber,
            qty = selectedSeats.value!!.size,
            tripId = selectedTrip.value!!.tripId.toString(),
            tripName = selectedTrip.value!!.tripName.toString(),
        ).also {
            _paymentRequest.value = it
        }
    }

    private fun encapsulateReservationRequest(): Result<ReservationRequest> =
        kotlin.runCatching {
            ReservationRequest(
                seats = selectedSeats.value!!.map { it.seatId!! }.toSet(),
                sourceBranchId = source.value!!.branchId,
                destinationBranchId = destination.value!!.branchId,
                reservationId = selectedTrip.value!!.reservations.first().id!!,
                tripId = selectedTrip.value!!.tripId!!,
                pathType = selectedTrip.value!!.pathType!!,
            )
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

    private fun createPaymentConfigurationDetails() {
        updateLoading(true)
        encapsulatePaymentConfigurationDetails()
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
            .onSuccess {
                updateLoading(false)
                _billingDetails.value = it
            }
    }

    fun whenPayButtonClicked() {
        when (selectedPaymentMethod.value) {
            PaymentMethod.Card -> {
                createPaymentConfigurationDetails()
            }

            PaymentMethod.EnglizyaWallet -> {
//                TODO("not implemented") //To change body of created functions use
            }
        }
    }

    private fun encapsulatePaymentConfigurationDetails() =
        kotlin.runCatching {
            PayTabsService.createPaymentConfigData(
                user = reservationOrder.value!!.user,
                tripName = selectedTrip.value!!.tripName!!,
                amount = calculateAmount(),
                cartId = reservationOrder.value!!.orderId
            )
        }

    private fun calculateAmount(): Double {
        return selectedTrip.value?.let {
            it.plan?.seatPrices?.first {
                it.source == source.value?.branchId &&
                        it.destination == destination.value?.branchId
            }?.vipPrice!! * selectedSeats.value!!.size
        }!!.toDouble()
    }

    fun clearTripList() {
        _trips.postValue(emptyList())
    }

    fun setTransactionRef(transactionReference: String?) {
        _transactionRef.value = transactionReference
    }

    fun confirmReservation() {
        updateLoading(true)
        encapsulateReservationConfirmationRequest()
            .onSuccess {
                updateLoading(false)
                confirmReservation(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun confirmReservation(request: ReservationConfirmationRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            updateLoading(true)
            reservationRepository.confirmReservation(request, dataStore.getToken())
                .onSuccess {
                    updateLoading(false)
                    _onlineTickets.postValue(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    private fun encapsulateReservationConfirmationRequest(): Result<ReservationConfirmationRequest> {
        return kotlin.runCatching {
            ReservationConfirmationRequest(
                transactionRef = transactionRef.value!!,
                reservationId = selectedTrip.value!!.reservations.first().id!!,
                tripId = selectedTrip.value!!.tripId!!,
                pathType = selectedTrip.value!!.pathType!!,
                seats = selectedSeats.value!!.map { it.seatId!! }.toSet(),
                sourceBranchId = source.value!!.branchId,
                destinationBranchId = destination.value!!.branchId,
            )
        }
    }

    fun setSelectedBookingOffice(stationTime: LineStationTime?) {
        _bookingOffice.value = stationTime
    }

    fun setSelectedPaymentMethod(method: PaymentMethod) {
        _selectedPaymentMethod.value = method
    }
}
