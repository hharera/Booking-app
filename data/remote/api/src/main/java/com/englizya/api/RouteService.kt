package com.englizya.api

import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes

interface RouteService {
    suspend fun getExternalLines(): List<ExternalRoutes>
    suspend fun getInternalLines(): List<InternalRoutes>


}