package com.englizya.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query(value = "SELECT * from User ")
    fun getUsers(): List<User?>

    @Query(value = "SELECT * from User limit 1")
    fun getUser(): User
}