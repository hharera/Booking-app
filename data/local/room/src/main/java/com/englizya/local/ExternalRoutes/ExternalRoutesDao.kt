package com.englizya.local.ExternalRoutes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
@Dao
interface ExternalRoutesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExternalRoutes(externalRoutes:List<ExternalRoutes>)



    @Query(value = "SELECT * from ExternalRoutes ")
    fun getExternalRoutes(): List<ExternalRoutes>
}