package com.englizya.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.model.request.LoginRequest
import com.englizya.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _formValidity = MutableLiveData<SignupFormState>()
    val formValidity: LiveData<SignupFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>()
    val redirectRouting: LiveData<String> = _redirectRouting

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (phoneNumber.value.isNullOrBlank()) {
            _formValidity.postValue(SignupFormState(phoneNumberError = R.string.phone_number_should_be_not_empty))
        } else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
            _formValidity.postValue(SignupFormState(phoneNumberError = R.string.phone_number_not_valid))
        } else {
            _formValidity.postValue(SignupFormState(isValid = true))
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }
}