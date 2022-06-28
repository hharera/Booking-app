package com.englizya.offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OffersViewModel constructor(
    private val offerRepository: OfferRepository,
    ): BaseViewModel() {

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    init {
        viewModelScope.launch(Dispatchers.IO) {


            getOffers()
        }
    }

    private fun getOffers() = viewModelScope.launch {
        updateLoading(true)
        offerRepository
            .getAllOffers()
            .onSuccess {
                updateLoading(false)
                _offers.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }
}