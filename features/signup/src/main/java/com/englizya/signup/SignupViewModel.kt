package com.englizya.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.model.request.LoginRequest
import com.englizya.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _loginOperationState = MutableLiveData<Boolean>()
    val loginOperationState: LiveData<Boolean> = _loginOperationState

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _formValidity = MutableLiveData<SignupFormState>()
    val formValidity: LiveData<SignupFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>()
    val redirectRouting: LiveData<String> = _redirectRouting

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkFormValidity()
    }

    fun setPassword(password: String)  {
        _password.value = password
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (phoneNumber.value.isNullOrBlank()) {
            _formValidity.postValue(SignupFormState(phoneNumberError = R.string.phone_number_should_be_not_empty))
        } else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
            _formValidity.postValue(SignupFormState(phoneNumberError = R.string.phone_number_not_valid))
        } else if (password.value.isNullOrBlank()) {
            _formValidity.postValue(SignupFormState(passwordError = R.string.password_should_be_not_empty))
        } else if (Validity.passwordIsValid(password.value!!).not()) {
            _formValidity.postValue(SignupFormState(passwordError = R.string.password_not_vlid))
        } else {
            _formValidity.postValue(SignupFormState(isValid = true))
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }

    suspend fun signup() {
        if ((formValidity.value != null).and(formValidity.value!!.isValid)) {
            signup(phoneNumber.value, password.value)
        }
    }

    private suspend fun signup(phoneNumber: String?, password: String?) {
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