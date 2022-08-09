package com.englizya.local.offers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.model.Offer

@Database(
    version = 1,
    entities = [Offer::class],
    exportSchema = true,
)
abstract class OfferDatabase : RoomDatabase(){
    abstract fun getMarketDao() : OfferDao
}