package com.englizya.local.offers

import androidx.room.*
import com.englizya.model.model.Offer
@Dao
interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffers(offer:List<Offer>)



    @Query(value = "SELECT * from Offer ")
    fun getOffers(): List<Offer>

    @Query(value = "DELETE from Offer")
    fun clearOffers()
}


