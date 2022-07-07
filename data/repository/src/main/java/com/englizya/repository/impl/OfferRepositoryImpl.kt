package com.englizya.repository.impl

import android.util.Log
import com.englizya.api.OfferService
import com.englizya.local.offers.OfferDao
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository

class OfferRepositoryImpl constructor(
    private val offerService: OfferService,
    private val offerDao: OfferDao
) : OfferRepository {
    override suspend fun getAllOffers(forceOnline: Boolean): Result<List<Offer>> =
        kotlin.runCatching {
            if (forceOnline) {
                offerService.getAllOffers().also {
                    offerDao.insertOffers(it)
                    Log.d("DataRemote", it.toString())

                }
            } else {
                offerDao.getOffers().also {
                    Log.d("DataLocal", it.toString())
                }

            }

        }

    override suspend fun getOfferDetails(offerId: String): Result<Offer> = kotlin.runCatching {
        offerService.getOfferDetails(offerId)
    }
}