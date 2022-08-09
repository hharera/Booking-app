package com.englizya.repository

import com.englizya.model.model.Offer
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface OfferRepository {

    fun getAllOffers(forceOnline: Boolean = false): Flow<Resource<List<Offer>>>
    fun getOffer(offerId: Int, forceOnline: Boolean = false): Flow<Resource<Offer>>
}