package com.englizya.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity

class SignupViewModel : BaseViewModel() {

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _formValidity = MutableLiveData<SignupFormState>()
    val formValidity: LiveData<SignupFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>()
    val redirectRouting: LiveData<String> = _redirectRouting

    private var _termsAccepted = MutableLiveData<Boolean>(false)
    val termsAccepted: LiveData<Boolean> = _termsAccepted

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (phoneNumber.value.isNullOrBlank()) {
            _formValidity.postValue(SignupFormState(phoneNumberError = R.string.empty_phone_error))
        } else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
            _formValidity.postValue(SignupFormState(phoneNumberError = R.string.phone_number_not_valid))
        } else if (_termsAccepted.value!!.not()) {
            _formValidity.postValue(SignupFormState(termsAcceptanceError = R.string.must_accept_terms))
        } else {
            _formValidity.postValue(SignupFormState(isValid = true))
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }

    fun whenAcceptedClicked() {
        _termsAccepted.value = _termsAccepted.value!!.not()
        checkFormValidity()
    }

    fun whenAccept() {
        _termsAccepted.value = true
        checkFormValidity()
    }
}