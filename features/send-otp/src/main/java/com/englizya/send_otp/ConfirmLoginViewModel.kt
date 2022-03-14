package com.englizya.send_otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Response
import com.englizya.common.utils.Validity
import com.englizya.repository.AuthenticationManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmLoginViewModel @Inject constructor(
    private val authenticationManager : AuthenticationManager,
) : BaseViewModel() {

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _codeValidity = MutableLiveData<Boolean>(false)
    val codeValidity: LiveData<Boolean> = _codeValidity

    private var _verificationCode = MutableLiveData<Response<String>>()
    val verificationCode: LiveData<Response<String>> = _verificationCode

    private var _confirmationState = MutableLiveData<Boolean>()
    val loginOperation: LiveData<Boolean> = _confirmationState

    private var _code = MutableLiveData<String>("")
    val code: LiveData<String> = _code

    fun putCharacter(ch: String) {
        code.value?.let {
            if (it.length < 6) {
                _code.value = it.plus(ch)
            }
        }
    }

    fun checkCodeValidity() {
        _codeValidity.value = Validity.checkCodeValidity(_code.value!!)
    }

    fun removeCharacter() {
        if (_code.value!!.isNotEmpty()) {
            _code.value = _code.value!!.dropLast(1)
        }
    }

    fun createCallBack() =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                _confirmationState.postValue(true)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _verificationCode.postValue(Response.error(e, null))
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _verificationCode.value = Response.success(verificationId)
            }
        }

    fun login() {
        updateLoading(true)

        viewModelScope.launch(Dispatchers.IO) {
            val credential = authenticationManager
                .createCredential(verificationCode.value!!.data!!, code.value!!)
            authenticationManager.login(credential)
                .addOnSuccessListener {
                    updateLoading(false)
                    _confirmationState.postValue(true)
                }
                .addOnFailureListener {
                    updateLoading(false)
                    _confirmationState.postValue(true)
                }
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.postValue(phoneNumber)
    }
}