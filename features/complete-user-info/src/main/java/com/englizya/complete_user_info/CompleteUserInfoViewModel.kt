package com.englizya.complete_user_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.code.CountryCode
import com.englizya.datastore.UserDataStore
import com.englizya.model.request.LoginRequest
import com.englizya.model.request.SignupRequest
import com.englizya.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompleteUserInfoViewModel constructor(
    private val userRepository: UserRepository,
    private val userDataStore: UserDataStore,

    private val auth: FirebaseAuth
) : BaseViewModel() {


    private val _signupOperationState = MutableLiveData<Boolean>()
    val signupOperationState: LiveData<Boolean> = _signupOperationState

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _loginOperationState = MutableLiveData<Boolean>()
    val loginOperationState: LiveData<Boolean> = _loginOperationState

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    fun setName(name: String) {
        _name.value = name
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun setPassword(password: String) {
        _password.value = password
    }
    fun validateForm(): Boolean {
        return !(_name.value == null ||
                _phoneNumber.value == null ||
                _password.value == null )
    }
    suspend fun signup() {
        val request = SignupRequest(
            uid = auth.uid!!,
            phoneNumber = CountryCode.EgyptianCode.code.plus(phoneNumber.value!!),
            password = password.value!!,
            name = name.value!!
        )

        signup(request)
    }
    private suspend fun signup(request: SignupRequest) {
        Log.d(TAG, "signup: $request")
        updateLoading(true)

        userRepository
            .signup(request)
            .onSuccess {
                updateLoading(false)
                _signupOperationState.value = true
            }
            .onFailure {
                handleException(it)
                updateLoading(false)
                _signupOperationState.value = false
            }
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
    private fun updateToken(token: String) {
        userDataStore.setToken(token)
    }
}