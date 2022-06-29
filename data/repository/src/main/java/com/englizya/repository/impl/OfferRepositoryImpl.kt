package com.englizya.repository.impl

import com.englizya.api.OfferService
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository

class OfferRepositoryImpl constructor(
    private val offerService: OfferService
) : OfferRepository {
    override suspend fun getAllOffers(): Result<List<Offer>> = kotlin.runCatching {
        offerService.getAllOffers()
    }

    override suspend fun getOfferDetails(offerId: String): Result<Offer> = kotlin.runCatching {
        offerService.getOfferDetails(offerId)
    }
}