package com.englizya.local.offers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.Offer
@Dao
interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffers(offer:List<Offer>)



    @Query(value = "SELECT * from Offer ")
    fun getOffers(): List<Offer>
}


