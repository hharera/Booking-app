package com.englizya.local.externalRoutes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.ExternalRoutes
import kotlinx.coroutines.flow.Flow

@Dao
interface ExternalRoutesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExternalRoutes(externalRoutes:List<ExternalRoutes>)



    @Query(value = "SELECT * from ExternalRoutes ")
    fun getExternalRoutes(): Flow<List<ExternalRoutes>>

    @Query(value = "DELETE from ExternalRoutes")
    fun clearExternalRoutes()
}