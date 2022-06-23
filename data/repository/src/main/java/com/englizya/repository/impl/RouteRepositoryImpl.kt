package com.englizya.repository.impl

import com.englizya.api.RouteService
import com.englizya.model.model.LineDetails
import com.englizya.repository.RouteRepository

class RouteRepositoryImpl constructor(
    private val routeService: RouteService
) :RouteRepository{


    override suspend fun getExternalLines(token: String): Result<List<LineDetails>>  = kotlin.runCatching {
        routeService.getExternalLines()
    }

}