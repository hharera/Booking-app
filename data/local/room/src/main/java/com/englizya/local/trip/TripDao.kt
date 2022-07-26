package com.englizya.local.trip

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.Trip


@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrips(trips:List<Trip>)



    @Query(value = "SELECT * from Trip ")
    fun getTrips(): List<Trip>
}