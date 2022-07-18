package com.englizya.local.User

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.model.User

@Database(
    version = 6,
    entities = [User::class],
    exportSchema = true,


)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getMarketDao(): UserDao
}