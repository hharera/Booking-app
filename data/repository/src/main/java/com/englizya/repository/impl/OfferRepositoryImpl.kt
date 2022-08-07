package com.englizya.repository.impl

import androidx.room.withTransaction
import com.englizya.api.OfferService
import com.englizya.local.offers.OfferDao
import com.englizya.local.offers.OfferDatabase
import com.englizya.model.model.Offer
import com.englizya.repository.OfferRepository
import com.englizya.repository.utils.Resource
import com.englizya.repository.utils.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class OfferRepositoryImpl constructor(
    private val offerService: OfferService,
    private val db: OfferDatabase,

    private val offerDao: OfferDao
) : OfferRepository {
    override  fun getAllOffers(): Flow<Resource<List<Offer>>> =
        networkBoundResource(
            query =
            {
                offerDao.getOffers()
            },
            fetch =
            {
                delay(2000)
                offerService.getAllOffers()
            },
            saveFetchResult =
            { offers ->
                db.withTransaction {
                    offerDao.clearOffers()
                    offerDao.insertOffers(offers)
                }


            })


    override suspend fun getOfferDetails(offerId: String): Result<Offer> = kotlin.runCatching {
        offerService.getOfferDetails(offerId)
    }
}