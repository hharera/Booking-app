package com.englizya.user_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.repository.UserRepository
import com.google.type.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserFormViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private var _address = MutableLiveData<LatLng>()
    val address: LiveData<LatLng> = _address

    private var _formValidity = MutableLiveData<UserFormState>()
    val formValidity: LiveData<UserFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>()
    val redirectRouting: LiveData<String> = _redirectRouting

    fun setPhoneNumber(phoneNumber: String) {
        _name.value = phoneNumber
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (name.value.isNullOrBlank()) {
            _formValidity.postValue(UserFormState(nameError = R.string.phone_number_should_be_not_empty))
        } else if (Validity.phoneNumberIsValid(name.value!!).not()) {
            _formValidity.postValue(UserFormState(nameError = R.string.phone_number_not_valid))
        } else {
            _formValidity.postValue(UserFormState(isValid = true))
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }

    fun setLatLng(redirect: String) {
        _redirectRouting.postValue(redirect)
    }

    fun setName(name: String) {
        _name.value = name
    }
}