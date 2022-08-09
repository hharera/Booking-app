package com.englizya.offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository
import com.englizya.repository.utils.Resource

class OffersViewModel constructor(
    private val offerRepository: OfferRepository,
) : BaseViewModel() {

    fun getOffer(offerId: Int): LiveData<Resource<Offer>> {
        return offerRepository
            .getOffer(offerId)
            .asLiveData()
    }

    fun getOffers(): LiveData<Resource<List<Offer>>> {
        return offerRepository
            .getAllOffers()
            .asLiveData()
    }
}