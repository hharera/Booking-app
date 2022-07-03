package com.englizya.repository.impl

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.englizya.api.RouteService
import com.englizya.local.ExternalRoutes.ExternalRoutesDatabase
import com.englizya.local.InternalRoutes.InternalRoutesDatabase
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteRepositoryImpl constructor(
    private val routeService: RouteService,
    private val internalRouteDatabase: InternalRoutesDatabase,
    private val externalRoutesDatabase: ExternalRoutesDatabase,
) : RouteRepository {


    override suspend fun getExternalLines(getForcedOnline: Boolean): Result<List<ExternalRoutes>> =
        kotlin.runCatching {
            if (getForcedOnline) {
                routeService.getExternalLines().also {
                    externalRoutesDatabase.getMarketDao().insertExternalRoutes(it)
                    Log.d("DataRemote",it.toString())
                }
            } else {
                getLocalExternalRoute()
            }
        }


    override suspend fun getInternalLines(getForcedOnline: Boolean): Result<List<InternalRoutes>> =
        kotlin.runCatching {
            if (getForcedOnline) {
                routeService.getInternalLines().also {
                    internalRouteDatabase.getMarketDao().insertInternalRoutes(it)
                    Log.d("DataRemote",it.toString())

                }
            } else {
                getLocalInternalRoute()
            }
        }


    private fun getLocalInternalRoute(): List<InternalRoutes> {
        Log.d("Datalocal",internalRouteDatabase.getMarketDao().getInternalRoutes().toString())

        return internalRouteDatabase.getMarketDao().getInternalRoutes()
    }

    private fun getLocalExternalRoute(): List<ExternalRoutes> {
        Log.d("Datalocal",externalRoutesDatabase.getMarketDao().getExternalRoutes().toString())
        return externalRoutesDatabase.getMarketDao().getExternalRoutes()
    }

}