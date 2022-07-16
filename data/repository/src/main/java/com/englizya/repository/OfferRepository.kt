package com.englizya.repository

import com.englizya.model.model.Offer

interface OfferRepository {
    suspend fun getAllOffers(forceOnline : Boolean = false): Result<List<Offer>>
    suspend fun getOfferDetails(offerId: String): Result<Offer>


}