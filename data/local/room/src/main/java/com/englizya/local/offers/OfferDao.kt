package com.englizya.local.offers

import androidx.room.*
import com.englizya.model.model.Offer
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffers(offer: List<Offer>)

    @Query(value = "SELECT * from Offer ")
    fun getOffers(): Flow<List<Offer>>

    @Query(value = "DELETE from Offer")
    fun clearOffers()

    @Query(value = "SELECT * from Offer where Offer.offerId = :offerId limit 1")
    fun getOffer(offerId: Int): Flow<Offer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffer(offer: Offer)

    @Delete()
    fun deleteOffer(offer: Offer)
}


