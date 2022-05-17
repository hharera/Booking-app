package com.englizya.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.utils.Value
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel constructor(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository,
    private val dataStore: UserDataStore,
) : BaseViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _userBalance = MutableStateFlow<Double?>(0.0)
    val userBalance: StateFlow<Double?> = _userBalance

    init {
        getUserBalance()
        fetchUser()
    }

    private fun fetchUser() = viewModelScope.launch {
        updateLoading(true)
        userRepository
            .fetchUser(dataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _user.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun getUserBalance() = viewModelScope.launch {
        walletRepository
            .getBalance(dataStore.getToken())
            .onSuccess {
                _userBalance.value = it.balance
            }
            .onFailure {
                handleException(it)
            }
    }

    fun logout() {
        dataStore.setToken(Value.NULL_STRING)
    }
}
