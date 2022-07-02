package com.englizya.repository.impl

import com.englizya.api.RouteService
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.RouteRepository

class RouteRepositoryImpl constructor(
    private val routeService: RouteService
) :RouteRepository{


    override suspend fun getExternalLines(): Result<List<ExternalRoutes>>  = kotlin.runCatching {
        routeService.getExternalLines()
    }

    override suspend fun getInternalLines(): Result<List<InternalRoutes>>  = kotlin.runCatching {
        routeService.getInternalLines()
    }


}