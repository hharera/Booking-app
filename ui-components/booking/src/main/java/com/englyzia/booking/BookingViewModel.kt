package com.englyzia.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.*
import com.englizya.model.request.*
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.OnlineTicket
import com.englizya.model.response.PayMobPaymentResponse
import com.englizya.model.response.ReservationOrder
import com.englizya.repository.*
import com.englyzia.booking.utils.BookingType
import com.englyzia.booking.utils.PaymentMethod
import com.englyzia.paytabs.PayTabsService
import com.englyzia.paytabs.dto.Invoice
import com.englyzia.paytabs.utils.PaymentMethod.Fawry
import com.englyzia.paytabs.utils.PaymentMethod.Meeza
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import com.englyzia.paytabs.utils.PaymentMethod as PaytabsUtilsPaymentMethod

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

    private var _trips = MutableLiveData<List<Trip>>(null)
    val trips: LiveData<List<Trip>> = _trips

    private var _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip> = _selectedTrip

    /**
     * Reservation Data
     */

    private var _reservationOrder = MutableLiveData<ReservationOrder>()
    val reservationOrder: LiveData<ReservationOrder> = _reservationOrder

    private var _paymentAction = MutableStateFlow<Boolean>(false)
    val paymentAction: StateFlow<Boolean> get() = _paymentAction

//    private var _user = MutableStateFlow<User?>(null)
//    val user: StateFlow<User?> get() = _user

    private var _paymentToken = MutableStateFlow<PayMobPaymentResponse?>(null)
    val paymentToken: StateFlow<PayMobPaymentResponse?> get() = this._paymentToken

    private var _billingDetails = MutableStateFlow<PaymentSdkConfigurationDetails?>(null)
    val billingDetails: StateFlow<PaymentSdkConfigurationDetails?> get() = this._billingDetails

    private var _total = MutableLiveData<Double>()
    val total: LiveData<Double> = _total


    private var _totalAfterDiscount = MutableLiveData<Double>()
    val totalAfterDiscount: LiveData<Double> = _totalAfterDiscount

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

    private var _selectedBookingOffice = MutableLiveData<LineStationTime?>()
    val selectedBookingOffice: LiveData<LineStationTime?> = _selectedBookingOffice

    private var _selectedPaymentMethod = MutableLiveData<PaymentMethod>()
    val selectedPaymentMethod: LiveData<PaymentMethod> = _selectedPaymentMethod

    private var _reservationWithWalletRequest = MutableLiveData<ReservationWithWalletRequest>()
    val reservationWithWalletRequest: LiveData<ReservationWithWalletRequest> =
        _reservationWithWalletRequest

    private var _invoicePaymentResponse = MutableLiveData<InvoicePaymentResponse>()
    val invoicePaymentResponse: LiveData<InvoicePaymentResponse> = _invoicePaymentResponse

    private var _bookingType = MutableLiveData<BookingType>(BookingType.OneWayBooking)
    val bookingType: LiveData<BookingType> = _bookingType
    var user = userRepository.getUser(dataStore.getToken(), false).asLiveData()

    init {
        setDefaultDate()
    }

    private fun setDefaultDate() {
        _date.value = (DateTime.now())
    }

     fun getUser(forceOnline : Boolean) = userRepository.getUser(dataStore.getToken(),forceOnline).asLiveData().let {
        user = it
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

    fun getBookingOffices() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        stationRepository.getAllStations()
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


    fun clearReservationOrder() {
        _reservationOrder.value = null
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

    fun searchTrips(forceOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        tripsRepository.searchTrips(
            TripSearchRequest(
                date = date.value!!.toString(),
                sourceStationId = source.value!!.branchId,
                destinationStationId = destination.value!!.branchId
            ), forceOnline
        ).onSuccess {
            setDefaultOffice(it)
            updateLoading(false)
            _trips.postValue(it)
        }.onFailure {
            updateLoading(false)
            handleException(it)
        }
    }

    private fun setDefaultOffice(it: List<Trip>) {
        _selectedBookingOffice.postValue(it.firstOrNull()?.tripTimes?.firstOrNull())
    }

    fun setSelectedTrip(trip: Trip) {
        _selectedTrip.value = trip
        updateBookingOffice(trip)
    }

    private fun updateBookingOffice(trip: Trip) {
        _selectedBookingOffice.value = trip.tripTimes.firstOrNull()
    }

    fun setSelectedSeat(seat: Seat): Boolean {
        if (selectedSeats.value?.size!! >= 6) {
            if (selectedSeats.value?.contains(seat) == true) {
                _selectedSeats.value = selectedSeats.value?.minus(seat)
                _totalAfterDiscount.value = updateAmount()
                _total.value = calculateAmount()
                Log.d("checkRoundReservation", checkRoundReservation(calculateAmount()).toString())
                return true
            }

        } else {
            if (selectedSeats.value?.contains(seat) == true) {
                _selectedSeats.value = selectedSeats.value?.minus(seat)
                _totalAfterDiscount.value = updateAmount()
                _total.value = calculateAmount()
                Log.d("checkRoundReservation", checkRoundReservation(calculateAmount()).toString())
                return true
            } else {
                _selectedSeats.value = selectedSeats.value?.plus(seat)
                _totalAfterDiscount.value = updateAmount()
                _total.value = calculateAmount()
                Log.d("checkRoundReservation", checkRoundReservation(calculateAmount()).toString())
                return false
            }
        }
        return true

    }

    private fun updateAmount(): Double {
        val total = calculateAmount()
        return if (bookingType.value == BookingType.RoundBooking) {
            total.minus(total * 0.1) * 2
        } else {
            total
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
                _reservationOrder.postValue(it)
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
            passenger = user.value!!.data?.name!!,
            phoneMobile = user.value!!.data?.phoneNumber!!,
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
        _total.value = 0.0
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
        updateLoading(true)
        when (selectedPaymentMethod.value) {
            PaymentMethod.Card -> {
                createPaymentConfigurationDetails()
            }

            PaymentMethod.EnglizyaWallet -> {
                createReservationWithWalletRequest()
            }

            PaymentMethod.FawryPayment -> {
                requestInvoicePaymentOrder()
            }

            PaymentMethod.MeezaPayment -> {
                requestInvoicePaymentOrder()
            }
            PaymentMethod.VodafonePayment -> {
                requestInvoicePaymentOrder()
            }
            PaymentMethod.OrangePayment -> {
                requestInvoicePaymentOrder()
            }
            PaymentMethod.EtisalatPayment -> {
                requestInvoicePaymentOrder()
            }
            else -> {}
        }
    }

    private fun requestInvoicePaymentOrder() {
        updateLoading(true)

        encapsulateInvoicePaymentOrderRequest()
            .onSuccess {
                requestInvoicePaymentOrder(it)
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun encapsulateInvoicePaymentOrderRequest(): Result<InvoicePaymentOrderRequest> =
        kotlin.runCatching {
            InvoicePaymentOrderRequest(orderId = reservationOrder.value!!.orderId)
        }

    private fun requestInvoicePaymentOrder(request: InvoicePaymentOrderRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository
                .requestInvoicePaymentOrder(request, dataStore.getToken())
                .onSuccess {
                    requestInvoicePayment()
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    private fun createInvoice() = kotlin.runCatching {
        PayTabsService.createInvoice(
            user.value?.data!!,
            selectedTrip.value!!.tripName!!,
            checkRoundReservation(calculateAmount()),
            reservationOrder.value!!.orderId,
            selectedSeats.value!!.size,
            getPaymentMethod()
        )
    }

    private fun checkRoundReservation(amount: Double): Double {
        return when (bookingType.value) {
            is BookingType.RoundBooking -> {
                amount.minus(amount * 0.1) * 2

                //  amount.times(2)
            }

            else -> {
                amount
            }
        }
    }

    private fun getPaymentMethod(): PaytabsUtilsPaymentMethod {
        return when (selectedPaymentMethod.value) {
            PaymentMethod.FawryPayment -> Fawry
            PaymentMethod.MeezaPayment -> Meeza
            else -> Fawry
        }
    }

    private fun requestInvoicePayment() {
        createInvoice()
            .onSuccess {
                requestInvoicePayment(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun requestInvoicePayment(invoice: Invoice) = viewModelScope.launch(Dispatchers.IO) {
        paymentRepository
            .requestInvoicePayment(request = invoice)
            .onSuccess {
                delay(3000)
                updateLoading(false)
                _invoicePaymentResponse.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun createReservationWithWalletRequest() {
        updateLoading(true)
        encapsulateReservationWithWalletRequest()
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
            .onSuccess {
                updateLoading(false)
                _reservationWithWalletRequest.value = it
            }
    }

    private fun encapsulatePaymentConfigurationDetails() =
        kotlin.runCatching {
            PayTabsService.createPaymentConfigData(
                user = reservationOrder.value!!.user,
                tripName = selectedTrip.value!!.tripName!!,
                amount = checkRoundReservation(calculateAmount()),
                cartId = reservationOrder.value!!.orderId
            )
        }

    private fun calculateAmount(): Double {
        return when (selectedTrip.value?.service?.serviceId) {
            1 -> {
                selectedTrip.value?.let {
                    it.plan?.seatPrices?.first {
                        it.source == source.value?.branchId &&
                                it.destination == destination.value?.branchId
                    }?.vipPrice!! * selectedSeats.value!!.size
                }!!.toDouble()
            }

            2 -> {
                selectedTrip.value?.let {
                    it.plan?.seatPrices?.first {
                        it.source == source.value?.branchId &&
                                it.destination == destination.value?.branchId
                    }?.economicPrice!! * selectedSeats.value!!.size
                }!!.toDouble()
            }

            3 -> {
                selectedTrip.value?.let {
                    it.plan?.seatPrices?.first {
                        it.source == source.value?.branchId &&
                                it.destination == destination.value?.branchId
                    }?.pullmanPrice!! * selectedSeats.value!!.size
                }!!.toDouble()
            }

            4 -> {
                selectedTrip.value?.let {
                    it.plan?.seatPrices?.first {
                        it.source == source.value?.branchId &&
                                it.destination == destination.value?.branchId
                    }?.pullmanPrice!! * selectedSeats.value!!.size
                }!!.toDouble()
            }

            7 -> {
                selectedTrip.value?.let {
                    it.plan?.seatPrices?.first {
                        it.source == source.value?.branchId &&
                                it.destination == destination.value?.branchId
                    }?.royalGoldPrice!! * selectedSeats.value!!.size
                }!!.toDouble()
            }

            8 -> {
                selectedTrip.value?.let {
                    it.plan?.seatPrices?.first {
                        it.source == source.value?.branchId &&
                                it.destination == destination.value?.branchId
                    }?.miniGoldPrice!! * selectedSeats.value!!.size
                }!!.toDouble()
            }

            else -> {
                Double.MAX_VALUE
            }
        }

    }

    fun clearTripList() {
        _trips.postValue(null)
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
                    _reservationTickets.postValue(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    fun confirmReservation(request: ReservationWithWalletRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            updateLoading(true)
            reservationRepository.confirmReservation(request, dataStore.getToken())
                .onSuccess {
                    updateLoading(false)
                    _reservationTickets.postValue(it)
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
                selectedBookingOffice = getSelectedBookingOffice(),
            )
        }
    }

    private fun encapsulateReservationWithWalletRequest(): Result<ReservationWithWalletRequest> {
        return kotlin.runCatching {
            ReservationWithWalletRequest(
                reservationId = selectedTrip.value!!.reservations.first().id!!,
                tripId = selectedTrip.value!!.tripId!!,
                pathType = selectedTrip.value!!.pathType!!,
                seats = selectedSeats.value!!.map { it.seatId!! }.toSet(),
                sourceBranchId = source.value!!.branchId,
                destinationBranchId = destination.value!!.branchId,
                selectedBookingOffice = getSelectedBookingOffice(),
            )
        }
    }

    private fun getSelectedBookingOffice(): Int {
        if (selectedBookingOffice.value == null) {
            return source.value!!.bookingOfficeList!!.first().officeId!!
        } else {
            return selectedBookingOffice.value!!.bookingOffice!!.officeId!!
        }
    }

    fun setSelectedBookingOffice(stationTime: LineStationTime?) {
        Log.d("selectedOffice" , stationTime?.bookingOffice.toString())
        _selectedBookingOffice.value = stationTime
    }

    fun setSelectedPaymentMethod(method: PaymentMethod) {
        _selectedPaymentMethod.value = method
    }

    fun swapStations() {
        _source.value?.let { source ->
            _destination.value?.let { destination ->
                _source.value = destination
                _destination.value = source
            }
        }
    }

    fun getStations() = viewModelScope.launch(Dispatchers.IO) {
        if (stations.value.isNullOrEmpty())
            getBookingOffices()
    }

    fun setBookingType(bookingType: BookingType) {
        _bookingType.value = bookingType
    }
}

