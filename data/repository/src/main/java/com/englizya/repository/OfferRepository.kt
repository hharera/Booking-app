package com.englizya.repository

import com.englizya.model.model.Offer
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface OfferRepository {
     fun getAllOffers(): Flow<Resource<List<Offer>>>
    suspend fun getOfferDetails(offerId: String): Result<Offer>


}