package com.englizya.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.repository.UserRepository

class SplashViewModel constructor(
    private val userRepository: UserRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    suspend fun checkLoginState() {
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
                _loginState.postValue(true)
            }
            .onFailure {
                _loginState.postValue(false)
            }
    }
}