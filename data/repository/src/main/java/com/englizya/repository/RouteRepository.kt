package com.englizya.repository

import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes

interface RouteRepository {
    suspend fun getExternalLines(getForcedOnline : Boolean = false): Result<List<ExternalRoutes>>
    suspend fun getInternalLines(getForcedOnline : Boolean = false): Result<List<InternalRoutes>>

}