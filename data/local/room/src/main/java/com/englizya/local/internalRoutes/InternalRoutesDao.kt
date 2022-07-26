package com.englizya.local.internalRoutes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.InternalRoutes

@Dao
interface InternalRoutesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInternalRoutes(internalRoutes:List<InternalRoutes>)



    @Query(value = "SELECT * from InternalRoutes ")
    fun getInternalRoutes(): List<InternalRoutes>

    @Query(value = "DELETE from InternalRoutes")
    fun clearInternalRoutes()
}