package com.englyzia.booking_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingPaymentViewModel constructor(
    private val walletRepository: WalletRepository,
    private val dataStore: UserDataStore,
) : BaseViewModel() {


    private var _userBalance = MutableStateFlow<Double?>(0.0)
    val userBalance: StateFlow<Double?> = _userBalance

    init {
        getUserBalance()

    }


     fun getUserBalance() = viewModelScope.launch {
        walletRepository
            .getBalance(dataStore.getToken())
            .onSuccess {
                _userBalance.value = it.balance
            }
            .onFailure {
                handleException(it)
            }
    }
}