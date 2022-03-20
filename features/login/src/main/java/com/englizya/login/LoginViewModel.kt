package com.englizya.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity.Companion.passwordIsValid
import com.englizya.common.utils.Validity.Companion.phoneNumberIsValid
import com.englizya.model.request.LoginRequest
import com.englizya.repository.UserRepository
import com.englizya.login.utils.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
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
        _phoneNumber.postValue(phoneNumber)
        checkFormValidity()
    }

    fun setPassword(password: String) {
        _password.postValue(password)
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (phoneNumber.value.isNullOrBlank()) {
            _formValidity.postValue(LoginFormState(phoneNumberError = R.string.phone_number_should_be_not_empty))
        } else if (phoneNumberIsValid(phoneNumber.value!!).not()) {
            _formValidity.postValue(LoginFormState(phoneNumberError = R.string.phone_number_not_valid))
        } else if (password.value.isNullOrBlank()) {
            _formValidity.postValue(LoginFormState(passwordError = R.string.password_should_be_not_empty))
        } else if (passwordIsValid(password.value!!).not()) {
            _formValidity.postValue(LoginFormState(passwordError = R.string.password_not_vlid))
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }

    suspend fun login() {
        if ((formValidity.value != null).and(formValidity.value!!.formIsValid)) {
            login(phoneNumber.value, password.value)
        }
    }

    private suspend fun login(phoneNumber: String?, password: String?) {
        password?.let { password ->
            phoneNumber?.let { phoneNumber ->
                LoginRequest(
                    phoneNumber,
                    password
                )
            }
        }?.let { loginRequest -> userRepository.login(loginRequest) }
    }
}
