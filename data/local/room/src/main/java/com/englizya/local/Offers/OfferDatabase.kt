package com.englizya.local.Offers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.local.UserDao
import com.englizya.model.model.Offer
import com.englizya.model.model.User

@Database(
    version = 1,
    entities = [Offer::class],
    exportSchema = true,


    )

abstract class OfferDatabase : RoomDatabase(){
    abstract fun getMarketDao() : OfferDao
}