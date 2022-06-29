package com.englizya.offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Announcement
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OffersViewModel constructor(
    private val offerRepository: OfferRepository,
    ): BaseViewModel() {

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private var _offersDetails = MutableLiveData<Offer>()
    val offersDetails: LiveData<Offer> = _offersDetails


    private val _offersId = MutableLiveData<String?>()
    val offersId: MutableLiveData<String?>
        get() = _offersId

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

     fun getOfferDetails(offerId : String?) = viewModelScope.launch {
        updateLoading(true)
        if(offerId != null){
            offerRepository
                .getOfferDetails(offerId)
                .onSuccess {
                    updateLoading(false)
                    _offersDetails.value = it
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }
        }

}