package com.harera.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel

class ResetPasswordViewModel : BaseViewModel() {

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber : LiveData<String> = _phoneNumber

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword : LiveData<String> = _confirmPassword

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.postValue(phoneNumber)
    }

    fun setPassword(password: String) {
        _password.postValue(password)
    }

    fun setConfirmPassword(password: String) {
        _confirmPassword.postValue(password)
    }
}
