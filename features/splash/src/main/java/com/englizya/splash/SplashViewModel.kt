package com.englizya.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel constructor(
    private val userRepository: UserRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun checkLoginState() = viewModelScope.launch {
        val token = userDataStore.getToken()
        if (token == NULL_STRING) {
            _loginState.postValue(false)
        } else {
            getUser(token)
        }
    }

    private suspend fun getUser(token: String) {
        userRepository
            .fetchUser(token)
            .onSuccess {
                updateUserDataStore(it)
                _loginState.postValue(true)
            }
            .onFailure {
                handleException(it)
                _loginState.postValue(false)
            }
    }

    private fun updateUserDataStore(it: User) {
        userDataStore.setUserName(it.name)
        userDataStore.setPhoneNumber(it.phoneNumber)
    }
}