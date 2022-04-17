package com.englizya.add_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.repository.UserRepository

class AddPaymentCardViewModel constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _cardOwner = MutableLiveData<String>()
    val cardOwner: LiveData<String> = _cardOwner

    private var _cardNumber = MutableLiveData<String>()
    val cardNumber: LiveData<String> = _cardNumber

    private var _cardCvv = MutableLiveData<String>()
    val cardCvv: LiveData<String> = _cardCvv

    private var _cardExpiration = MutableLiveData<String>()
    val cardExpiration: LiveData<String> = _cardExpiration

    private var _formValidity = MutableLiveData<PaymentCardFormState>()
    val formValidity: LiveData<PaymentCardFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>()
    val redirectRouting: LiveData<String> = _redirectRouting

    fun setCardOwner(cardOwner: String) {
        _cardOwner.value = cardOwner
        checkFormValidity()
    }

    fun setCardNumber(cardNumber: String) {
        _cardNumber.value = cardNumber
        checkFormValidity()
    }

    fun setCardCvv(cvv: String) {
        _cardCvv.value = cvv
        checkFormValidity()
    }

    fun setCardExpiration(name: String) {
        _cardExpiration.value = name
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (cardNumber.value.isNullOrBlank()) {
            _formValidity.postValue(PaymentCardFormState(cardNumberError = R.string.card_number_is_required))
        } else if (Validity.cardNumberIsValid(cardNumber.value!!).not()) {
            _formValidity.postValue(PaymentCardFormState(cardNumberError = R.string.card_number_not_valid))
//        }
            //        else if (Validity.cardNumberIsValid(cardNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(cardNumberError = R.string.card_cvv_is_required))
//        }   else if (Validity.cardNumberIsValid(cardNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(cardNumberError = R.string.card_number_not_valid))
//        }  else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(phoneNumberError = R.string.phone_number_not_valid))
//        }  else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(phoneNumberError = R.string.phone_number_not_valid))
//        }  else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(phoneNumberError = R.string.phone_number_not_valid))
//        }  else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(phoneNumberError = R.string.phone_number_not_valid))
//        }  else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(phoneNumberError = R.string.phone_number_not_valid))
//        }  else if (Validity.phoneNumberIsValid(phoneNumber.value!!).not()) {
//            _formValidity.postValue(PaymentCardFormState(phoneNumberError = R.string.phone_number_not_valid))
//        } else {
//            _formValidity.postValue(PaymentCardFormState(isValid = true))
//        }
        }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }
}