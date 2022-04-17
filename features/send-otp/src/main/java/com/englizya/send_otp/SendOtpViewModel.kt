package com.englizya.send_otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.repository.UserRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

class SendOtpViewModel constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _code = MutableLiveData<String>()
    val code: LiveData<String> = _code

    private var _verificationCode = MutableLiveData<String>()
    val verificationCode: LiveData<String> = _verificationCode

    private var _codeValidity = MutableLiveData<Boolean>(false)
    val codeValidity: LiveData<Boolean> = _codeValidity

    private var _resendToken = MutableLiveData<ForceResendingToken>()
    val refreshToken: LiveData<ForceResendingToken> = _resendToken

    private var _verificationState = MutableLiveData<Boolean>()
    val verificationState: LiveData<Boolean> = _verificationState

    fun putCharacter(numberCharacter: String) {
        if (code.value.isNullOrBlank())
            _code.value = numberCharacter
        else if (code.value!!.length < 6)
            _code.value = code.value!!.plus(numberCharacter)

        checkCodeValidity()
    }

    fun removeCharacter() {
        if (null != _code.value && _code.value!!.isNotBlank()) {
            _code.value = _code.value!!.dropLast(1)
        }

        checkCodeValidity()
    }

    private fun checkCodeValidity() {
        _codeValidity.value = Validity.checkCodeValidity(_code.value!!)
    }

    fun getVerificationCallBack() =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                TODO()
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

    fun signup() {
        signup(verificationCode = verificationCode.value, code = code.value)
    }

    private fun signup(verificationCode: String?, code: String?) {
        if (verificationCode == null || code == null) {
            return
        }

        updateLoading(true)

        val credential = userRepository.createCredential(verificationCode, code)

        userRepository.signup(credential)
            .addOnSuccessListener { authResult ->
                updateLoading(false)
                _verificationState.postValue(true)
            }
            .addOnFailureListener {
                updateLoading(false)
                _verificationState.postValue(false)
            }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = (phoneNumber)
    }
}