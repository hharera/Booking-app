package com.englizya.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.dto.Offer
import com.englizya.model.request.LoginRequest
import com.englizya.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _loginOperationState = MutableLiveData<Boolean>()
    val loginOperationState: LiveData<Boolean> = _loginOperationState

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    fun setOffers(offers: List<Offer>) {
        _offers.value = offers
    }

    suspend fun login() {
        if ((formValidity.value != null).and(formValidity.value!!.formIsValid)) {
            login(_offers.value, password.value)
        }
    }

    private suspend fun login(phoneNumber: String?, password: String?) {
        password?.let { password ->
            phoneNumber?.let { phoneNumber ->
                LoginRequest(
                    phoneNumber,
                    password
                )
            }
        }?.let { loginRequest -> userRepository.login(loginRequest) }
    }
}
