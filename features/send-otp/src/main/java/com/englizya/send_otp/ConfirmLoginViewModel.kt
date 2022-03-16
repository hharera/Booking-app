package com.englizya.send_otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.repository.AuthenticationManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.lang.Exception
import javax.inject.Inject

class ConfirmLoginViewModel @Inject constructor(
    private val authenticationManager : AuthenticationManager,
) : BaseViewModel() {

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _codeValidity = MutableLiveData<Boolean>(false)
    val codeValidity: LiveData<Boolean> = _codeValidity

    private var _verificationCode = MutableLiveData<String>()
    val verificationCode: LiveData<String> = _verificationCode

    private var _resendToken = MutableLiveData<ForceResendingToken>()
    val refreshToken: LiveData<ForceResendingToken> = _resendToken

    private var _verificationState = MutableLiveData<Boolean>()
    val verificationState: LiveData<Boolean> = _verificationState

    private var _code = MutableLiveData<String>("")
    val code: LiveData<String> = _code

    fun putCharacter(numberCharacter: String) {
        code.value?.let {
            if (it.length < 6) {
                _code.postValue(it.plus(numberCharacter))
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

    fun getVerificationCallBack() =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                _verificationState.postValue(true)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                handleException(e)
            }

            override fun onCodeSent(
                verificationId: String,
                resendingToken: ForceResendingToken
            ) {
                _verificationCode.value = verificationId
                _resendToken.postValue(resendingToken)
            }
        }

    suspend fun signup() {
        if (checkCode().not()) {
            handleException(Exception("Invalid code"))
        }

        signup(verificationCode = verificationCode.value!!, code = code.value!!)
    }

    private fun checkCode() =
        verificationCode.value.equals(code.value).not()

    private suspend fun signup(verificationCode: String, code: String) {
        updateLoading(true)

        val credential = authenticationManager.createCredential(verificationCode, code)

        authenticationManager.signup(credential)
            .addOnSuccessListener {
                updateLoading(false)
                _verificationState.postValue(true)
            }
            .addOnFailureListener {
                updateLoading(false)
                _verificationState.postValue(false)
            }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.postValue(phoneNumber)
    }
}