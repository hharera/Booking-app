package com.englizya.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity

class ForgetPasswordViewModel : BaseViewModel() {

    //    phone number validity mutable live data
    private val _phoneNumberValidity = MutableLiveData<Boolean>()
    val phoneNumberValidity: LiveData<Boolean>
        get() = _phoneNumberValidity

    //    phone number mutable live data
    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkPhoneNumberValidity(phoneNumber)
    }

    private fun checkPhoneNumberValidity(phoneNumber: String) {
        _phoneNumberValidity.value = Validity.phoneNumberIsValid(phoneNumber)
    }

}