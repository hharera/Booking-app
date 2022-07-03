package com.englizya.offers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.local.Offers.OfferDatabase
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OffersViewModel constructor(
    private val offerRepository: OfferRepository,
    private val offerDatabase: OfferDatabase,
) : BaseViewModel() {

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private var _offersDetails = MutableLiveData<Offer>()
    val offersDetails: LiveData<Offer> = _offersDetails


    private val _offersId = MutableLiveData<String?>()
    val offersId: MutableLiveData<String?>
        get() = _offersId

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//              getOffersLocal()
//            getOffers()
//        }
//    }

     fun getOffers(forceOnline: Boolean) = viewModelScope.launch (Dispatchers.IO){
        updateLoading(true)
        offerRepository
            .getAllOffers(forceOnline)
            .onSuccess {

                viewModelScope.launch(Dispatchers.IO) {
                    offerDatabase.getMarketDao().insertOffers(it)
                    Log.d("Offers", offerDatabase.getMarketDao().getOffers().toString())
                }
                updateLoading(false)
                _offers.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun getOfferDetails(offerId: String?) = viewModelScope.launch {
        updateLoading(true)
        if (offerId != null) {
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

//    private fun getOffersLocal() {
//        offerDatabase.getMarketDao().getOffers().let {
//
//            _offers.postValue(it)
//        }
//    }
}