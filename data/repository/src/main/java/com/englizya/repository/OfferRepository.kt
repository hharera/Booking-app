package com.englizya.repository

import com.englizya.model.model.Offer

interface OfferRepository {
    suspend fun getAllOffers(): Result<List<Offer>>

}