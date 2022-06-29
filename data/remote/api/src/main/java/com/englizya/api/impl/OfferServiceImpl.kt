package com.englizya.api.impl

import com.englizya.api.OfferService
import com.englizya.api.utils.Routing
import com.englizya.model.model.Offer
import io.ktor.client.*
import io.ktor.client.request.*

class OfferServiceImpl constructor(
    private val client: HttpClient
): OfferService{
    override suspend fun getAllOffers(): List<Offer>  =
        client.get(Routing.GET_OFFERS)

    override suspend fun getOfferDetails(offerId: String): Offer =
        client.get(Routing.GET_OFFER_DETAILS+offerId.toInt())
}