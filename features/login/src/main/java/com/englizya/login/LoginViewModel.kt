package com.englizya.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity.Companion.passwordIsValid
import com.englizya.common.utils.Validity.Companion.phoneNumberIsValid
import com.englizya.common.utils.code.CountryCode
import com.englizya.model.request.LoginRequest
import com.englizya.datastore.UserDataStore
import com.englizya.login.utils.LoginFormState
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val userRepository: UserRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _loginOperationState = MutableLiveData<Boolean>()
    val loginOperationState: LiveData<Boolean> = _loginOperationState

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _formValidity = MutableLiveData<LoginFormState>()
    val formValidity: LiveData<LoginFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>(null)
    val redirectRouting: LiveData<String> = _redirectRouting

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkFormValidity()
    }

    fun setPassword(password: String) {
        _password.value = password
        checkFormValidity()
    }

    private fun checkFormValidity() {
        Log.d(TAG, "checkFormValidity: ${_phoneNumber.value}")
        when {
            _phoneNumber.value.isNullOrBlank() -> {
                _formValidity.postValue(LoginFormState(phoneNumberError = R.string.empty_phone_error))
            }
            phoneNumberIsValid(_phoneNumber.value!!).not() -> {
                _formValidity.postValue(LoginFormState(phoneNumberError = R.string.phone_number_not_valid))
            }
            password.value.isNullOrBlank() -> {
                _formValidity.postValue(LoginFormState(passwordError = R.string.password_should_be_not_empty))
            }
            passwordIsValid(password.value!!).not() -> {
                _formValidity.postValue(LoginFormState(passwordError = R.string.password_not_vlid))
            }
            else -> {
                _formValidity.postValue(LoginFormState(formIsValid = true))
            }
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }

    fun login() = viewModelScope.launch(Dispatchers.IO) {
        phoneNumber.value?.let { phoneNumber ->
            password.value?.let { password ->
                login(LoginRequest(CountryCode.EgyptianCode.code.plus(phoneNumber), password))
            }
        }
    }

    private suspend fun login(loginRequest: LoginRequest) {
        updateLoading(true)

        userRepository
            .login(loginRequest)
            .onSuccess {
                updateLoading(false)
                updateToken(it.jwt)
                userRepository.getUser(userDataStore.getToken(), true)
                _loginOperationState.postValue(true)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
                _loginOperationState.postValue(false)
            }
    }

//    private suspend fun getUserDetails() {
//        userRepository
//            .onSuccess {
////                cacheUser(it)
//            }
//            .onFailure {
//
//            }
//    }

//    private suspend fun cacheUser(user: User) {
//        userRepository
//            .insertUser(user)
//            .onSuccess {
//
//            }
//            .onFailure {
//
//            }
//    }

    private fun updateToken(token: String) {
        userDataStore.setToken(token)
    }
}
