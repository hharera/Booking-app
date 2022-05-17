package com.englizya.charging

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.englizya.charging.utils.Validator.isValidAmount
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.User
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.RechargingRequest
import com.englizya.model.response.PaymentOrder
import com.englizya.repository.PaymentRepository
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import com.englyzia.paytabs.PayTabsService
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RechargingViewModel constructor(
    private val walletRepository: WalletRepository,
    private val paymentRepository: PaymentRepository,
    private val userRepository: UserRepository,
    private val dataStore: UserDataStore,
) : BaseViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?>
        get() = _user

    private val _amount = MutableStateFlow<Double?>(null)
    val amount: StateFlow<Double?>
        get() = _amount

    private val _paymentOrder = MutableStateFlow<PaymentOrder?>(null)
    val paymentOrder: StateFlow<PaymentOrder?>
        get() = _paymentOrder

    private val _formValidity = MutableStateFlow<RechargingFormState?>(null)
    val formValidity: StateFlow<RechargingFormState?>
        get() = _formValidity

    private var _billingDetails = MutableStateFlow<PaymentSdkConfigurationDetails?>(null)
    val billingDetails: StateFlow<PaymentSdkConfigurationDetails?> get() = this._billingDetails

    private var _transactionReference = MutableStateFlow<String?>(null)
    val transactionReference: StateFlow<String?> get() = this._transactionReference

    private var _rechargingOperationState = MutableStateFlow<Boolean?>(null)
    val rechargingOperationState: StateFlow<Boolean?> get() = this._rechargingOperationState

    init {
        viewModelScope
            .launch {
                userRepository
                    .fetchUser(dataStore.getToken())
                    .getOrNull()
                    ?.let {
                        _user.value = it
                    }
            }
    }


    private fun checkFormValidity() {
        if (amount.value == null) {
            _formValidity.value = RechargingFormState(amountRes = R.string.charging_amount_empty)
        } else if (isValidAmount(amount.value.toString()).not()) {
            _formValidity.value = RechargingFormState(amountRes = R.string.invalid_amount_empty)
        } else {
            _formValidity.value = RechargingFormState(formIsValid = true)
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

    fun rechargeBalance() = viewModelScope.launch {
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

    private suspend fun requestPayment(request: PaymentOrderRequest) {
        updateLoading(true)

        Log.d(TAG, "requestPayment: $request")
        walletRepository
            .requestRecharge(dataStore.getToken(), request)
            .onSuccess {
                updateLoading(false)
                _paymentOrder.value = it
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun rechargeBalance(transactionReference: String?) = viewModelScope.launch {
        if (transactionReference == null) {
            return@launch
        }

        updateLoading(true)

        walletRepository
            .rechargeBalance(dataStore.getToken(), RechargingRequest(transactionReference))
            .onSuccess {
                updateLoading(false)
                _rechargingOperationState.value = true
            }.onFailure {
                updateLoading(false)
                handleException(it)
                _rechargingOperationState.value = false
            }
    }


    fun rechargeBalance(paymentOrder: PaymentOrder) {
        updateLoading(true)

        encapsulatePaymentConfigurationDetails(paymentOrder)
            .onSuccess {
                updateLoading(false)
                _billingDetails.value = it
            }
            .onFailure {
                updateLoading(false)
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