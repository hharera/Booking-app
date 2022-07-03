package com.englizya.repository

import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes

interface RouteRepository {
    suspend fun getExternalLines(getForcedOnline : Boolean): Result<List<ExternalRoutes>>
    suspend fun getInternalLines(getForcedOnline : Boolean): Result<List<InternalRoutes>>

}