package com.englizya.local.offers

import androidx.room.*
import com.englizya.model.model.Offer
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffers(offer:List<Offer>)

    @Query(value = "SELECT * from Offer ")
    fun getOffers(): Flow<List<Offer>>

    @Query(value = "DELETE from Offer")
    fun clearOffers()
}


