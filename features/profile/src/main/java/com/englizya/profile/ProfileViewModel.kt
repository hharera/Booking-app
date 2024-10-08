package com.englizya.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.utils.Value
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel constructor(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository,
     val dataStore: UserDataStore,
    private val auth: FirebaseAuth

    ) : BaseViewModel() {


    private var _userBalance = MutableStateFlow<Double?>(0.0)
    val userBalance: StateFlow<Double?> = _userBalance

    private var _logOutState = MutableLiveData<Boolean>()
    val logoutState : LiveData<Boolean> =_logOutState
    var user = userRepository.getUser(dataStore.getToken(), false).asLiveData()

    init {
        getUserBalance()

    }

    fun fetchUser(forceOnline: Boolean) =
        userRepository.getUser(dataStore.getToken(), forceOnline).asLiveData().let {
            user = it
        }


    fun getUserBalance() = viewModelScope.launch(Dispatchers.IO) {
        walletRepository
            .getBalance(dataStore.getToken())
            .onSuccess {
                _userBalance.value = it.balance
            }
            .onFailure {
                handleException(it)
            }
    }
fun authLogout(){
    if(auth.currentUser != null){
        auth.signOut()
    }
}
    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.logOut().onSuccess {
            _logOutState.postValue(true)
        }.onFailure {
            _logOutState.postValue(false)
        }
        dataStore.setToken(Value.NULL_STRING)
    }
}
