package com.englizya.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.model.request.ResetPasswordRequest
import com.englizya.repository.UserRepository
import kotlinx.coroutines.launch

class ResetPasswordViewModel constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _formValidity = MutableLiveData<Boolean>()
    val formValidity: LiveData<Boolean> = _formValidity

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean> = _operationSuccess

    fun setPassword(password: String) {
        _password.postValue(password)
        checkValidity()
    }

    fun setConfirmPassword(password: String) {
        _confirmPassword.postValue(password)
        checkValidity()
    }

    private fun checkValidity() {
        _formValidity.postValue(
            _password.value.isNullOrBlank().not() &&
                    _confirmPassword.value.isNullOrBlank().not() &&
                    Validity.passwordIsValid(password.value) &&
                    Validity.passwordIsValid(confirmPassword.value) &&
                    _password.value == _confirmPassword.value
        )
    }

    suspend fun resetPassword() {
        userRepository
            .getCurrentUser()
            ?.getIdToken(true)
            ?.addOnSuccessListener {
                it.token?.let { it1 -> resetPassword(it1) }
            }
    }

    private fun resetPassword(token: String) = viewModelScope.launch {
        updateLoading(true)
        userRepository.resetPassword(
            ResetPasswordRequest(
                authenticationId = token,
                password = password.value!!,
            )
        ).onSuccess {
            _operationSuccess.postValue(true)
        }.onFailure {
            handleException(it)
        }
    }
}
