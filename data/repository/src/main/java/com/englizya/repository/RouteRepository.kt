package com.englizya.repository

import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes

interface RouteRepository {
    suspend fun getExternalLines(): Result<List<ExternalRoutes>>
    suspend fun getInternalLines(): Result<List<InternalRoutes>>

}