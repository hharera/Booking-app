package com.englizya.api

import com.englizya.model.model.Offer

interface OfferService {

    suspend fun getAllOffers(): List<Offer>
    suspend fun getOffer(offerId: Int) : Offer
}