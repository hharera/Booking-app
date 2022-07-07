package com.englizya.local.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.model.User

@Database(
    version = 2,
    entities = [User::class],
    exportSchema = true,
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getDao(): UserDao
}