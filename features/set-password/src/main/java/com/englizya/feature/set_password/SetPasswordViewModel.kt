package com.englizya.feature.set_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.model.request.SignupRequest
import com.englizya.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
) : BaseViewModel() {

    private val _signupOperationState = MutableLiveData<Boolean>()
    val signupOperationState: LiveData<Boolean> = _signupOperationState

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _formValidity = MutableLiveData<PasswordFormState>()
    val formValidity: LiveData<PasswordFormState> = _formValidity

    fun setName(name: String) {
        _name.value = name
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun setPassword(password: String) {
        _password.value = password
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (password.value.isNullOrBlank()) {
            _formValidity.postValue(PasswordFormState(passwordError = R.string.password_should_be_not_empty))
        } else if (Validity.passwordIsValid(password.value!!).not()) {
            _formValidity.postValue(PasswordFormState(passwordError = R.string.password_not_vlid))
        } else {
            _formValidity.postValue(PasswordFormState(isValid = true))
        }
    }

    suspend fun signup() {
        val request = SignupRequest(
            uid = auth.uid!!,
            phoneNumber = phoneNumber.value!!,
            password = password.value!!,
        )

        signup(request)
    }

    private suspend fun signup(request: SignupRequest) {
        updateLoading(true)

        userRepository
            .signup(request)
            .onSuccess {
                updateLoading(false)
                _signupOperationState.value = true
            }
            .onFailure {
                updateLoading(false)
                _signupOperationState.value = false
            }
    }
}