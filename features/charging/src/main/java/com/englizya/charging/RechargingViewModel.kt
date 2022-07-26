package com.englizya.charging

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.charging.utils.RechargeMethod
import com.englizya.charging.utils.Validator.isValidAmount
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.User
import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.RechargingRequest
import com.englizya.model.request.ReservationRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englizya.model.response.ReservationOrder
import com.englizya.repository.PaymentRepository
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import com.englyzia.paytabs.PayTabsService
import com.englyzia.paytabs.dto.Invoice
import com.englyzia.paytabs.utils.PaymentMethod
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RechargingViewModel constructor(
    private val walletRepository: WalletRepository,
    private val paymentRepository: PaymentRepository,
    private val userRepository: UserRepository,
    private val dataStore: UserDataStore,
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _amount = MutableStateFlow<Double?>(null)
    val amount: StateFlow<Double?>
        get() = _amount

    private var _selectedRechargeMethod = MutableLiveData<RechargeMethod>()
    val selectedRechargeMethod: LiveData<RechargeMethod> = _selectedRechargeMethod

    private var _invoicePaymentResponse = MutableLiveData<InvoicePaymentResponse>()
    val invoicePaymentResponse: LiveData<InvoicePaymentResponse> = _invoicePaymentResponse

    private val _paymentOrder = MutableStateFlow<PaymentOrder?>(null)
    val paymentOrder: StateFlow<PaymentOrder?>
        get() = _paymentOrder


    private var _paymentInvoiceOrder = MutableLiveData<PaymentOrder>()
    val paymentInvoiceOrder: LiveData<PaymentOrder> = _paymentInvoiceOrder

    private var _formValidity = MutableLiveData<RechargingFormState>()
    val formValidity: LiveData<RechargingFormState> = _formValidity

    private var _billingDetails = MutableStateFlow<PaymentSdkConfigurationDetails?>(null)
    val billingDetails: StateFlow<PaymentSdkConfigurationDetails?> get() = this._billingDetails

    private var _transactionReference = MutableStateFlow<String?>(null)
    val transactionReference: StateFlow<String?> get() = this._transactionReference

    private var _rechargingOperationState = MutableStateFlow<Boolean?>(null)
    val rechargingOperationState: StateFlow<Boolean?> get() = this._rechargingOperationState

    init {
        getUser()
    }


    private fun checkFormValidity() {
        if (amount.value == null) {
            _formValidity.postValue(RechargingFormState(amountRes = R.string.charging_amount_empty))
        } else if (isValidAmount(amount.value.toString()).not()) {
            _formValidity.postValue(RechargingFormState(amountRes = R.string.invalid_amount_empty))
        } else if (isValidAmount(amount.value!!).not()) {
            _formValidity.postValue(RechargingFormState(amountRes = R.string.invalid_amount))
        } else {
            _formValidity.postValue(RechargingFormState(formIsValid = true))
        }
    }

    fun setAmount(amount: String?) {
        if (amount == null) {
            return
        }

        if (isValidAmount(amount)) {
            _amount.value = amount.toDouble()
        }

        checkFormValidity()
    }

    fun setSelectedPaymentMethod(method: RechargeMethod) {
        _selectedRechargeMethod.value = method
    }

    fun whenRechargeButtonClicked() {
        when (selectedRechargeMethod.value) {
            RechargeMethod.Card -> {
                rechargeBalance()
            }

            RechargeMethod.FawryPayment -> {
                rechargeBalanceByInvoice()

            }

            RechargeMethod.MeezaPayment -> {
                rechargeBalanceByInvoice()

            }
            RechargeMethod.VodafonePayment -> {
                rechargeBalanceByInvoice()
            }
            RechargeMethod.OrangePayment -> {
                rechargeBalanceByInvoice()

            }
            RechargeMethod.EtisalatPayment -> {
                rechargeBalanceByInvoice()

            }
            else -> {}
        }
    }

    private fun rechargeBalanceByInvoice() = viewModelScope.launch(Dispatchers.IO) {
        encapsulatePaymentOrderRequest()
            .onSuccess {
                requestPaymentByInvoice(it)
            }.onFailure {
                handleException(it)
            }
    }

    private suspend fun requestPaymentByInvoice(request: PaymentOrderRequest) {
        updateLoading(true)

        Log.d(TAG, "requestPayment: $request")
        walletRepository
            .requestRecharge(dataStore.getToken(), request)
            .onSuccess {
                updateLoading(false)

                _paymentInvoiceOrder.postValue(it)
            }.onFailure {
                updateLoading(false)

                handleException(it)
            }
    }

    fun requestRechargeByInvoice(paymentOrder: PaymentOrder) {
        encapsulateInvoicePaymentOrderRequest(paymentOrder)
            .onSuccess {
                Log.d("requestRechargeByInvoice", "1")

                requestInvoicePayment()
            }.onFailure {
                handleException(it)
            }
    }

    private fun encapsulateInvoicePaymentOrderRequest(paymentOrder: PaymentOrder): Result<InvoicePaymentOrderRequest> =
        kotlin.runCatching {
            InvoicePaymentOrderRequest(orderId = paymentOrder.orderId)
        }


    private fun requestInvoicePayment() {
        createInvoice()
            .onSuccess {
                Log.d("requestInvoicePayment", "2")

                requestInvoicePayment(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun requestInvoicePayment(invoice: Invoice) = viewModelScope.launch {
        walletRepository
            .requestInvoicePayment(request = invoice)
            .onSuccess {
                Log.d("requestInvoicePayment", it.toString())
                _invoicePaymentResponse.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun createInvoice() = kotlin.runCatching {
        PayTabsService.createInvoice(
            user.value!!,
            amount.value!!,
            paymentInvoiceOrder.value!!.orderId,
            getRechargeMethod()
        )
    }

    private fun getRechargeMethod(): PaymentMethod {
        return when (selectedRechargeMethod.value) {
            RechargeMethod.FawryPayment -> PaymentMethod.Fawry
            RechargeMethod.MeezaPayment -> PaymentMethod.Meeza
            else -> PaymentMethod.Fawry
        }
    }


    fun rechargeBalance() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        encapsulatePaymentOrderRequest()
            .onSuccess {
                updateLoading(false)
                requestPayment(it)
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }


    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        userRepository
            .getUser(dataStore.getToken(), true)
            .onSuccess {
                _user.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun requestPayment(request: PaymentOrderRequest) {

        Log.d(TAG, "requestPayment: $request")
        walletRepository
            .requestRecharge(dataStore.getToken(), request)
            .onSuccess {
                _paymentOrder.value = it
            }.onFailure {
                handleException(it)
            }
    }

    fun rechargeBalance(transactionReference: String?) = viewModelScope.launch(Dispatchers.IO) {
        if (transactionReference == null) {
            return@launch
        }


        walletRepository
            .rechargeBalance(dataStore.getToken(), RechargingRequest(transactionReference))
            .onSuccess {
                _rechargingOperationState.value = true
            }.onFailure {
                handleException(it)
                _rechargingOperationState.value = false
            }
    }

    fun rechargeBalance(paymentOrder: PaymentOrder) {

        encapsulatePaymentConfigurationDetails(paymentOrder)
            .onSuccess {
                _billingDetails.value = it
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun encapsulatePaymentOrderRequest(): Result<PaymentOrderRequest> =
        kotlin.runCatching {
            PaymentOrderRequest(
                amount = amount.value!!,
            )
        }

    private fun encapsulatePaymentConfigurationDetails() =
        kotlin.runCatching {
            PayTabsService.createPaymentConfigData(
                user = user.value!!,
                amount = amount.value!!,
                cartId = paymentOrder.value!!.orderId
            )
        }

    private fun encapsulatePaymentConfigurationDetails(paymentOrder: PaymentOrder) =
        kotlin.runCatching {
            PayTabsService.createPaymentConfigData(
                user = user.value!!,
                amount = amount.value!!,
                cartId = paymentOrder.orderId
            )
        }

    fun setTransactionRef(transactionReference: String?) {
        _transactionReference.value = transactionReference
    }
}