package com.englizya.repository.impl

import com.englizya.api.RouteService
import com.englizya.model.model.LineDetails
import com.englizya.model.model.Routes
import com.englizya.repository.RouteRepository

class RouteRepositoryImpl constructor(
    private val routeService: RouteService
) :RouteRepository{


    override suspend fun getExternalLines(): Result<List<Routes>>  = kotlin.runCatching {
        routeService.getExternalLines()
    }

    override suspend fun getInternalLines(): Result<List<Routes>>  = kotlin.runCatching {
        routeService.getInternalLines()
    }


}