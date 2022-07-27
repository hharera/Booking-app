package com.englizya.repository.impl

import android.util.Log
import com.englizya.api.RouteService
import com.englizya.local.externalRoutes.ExternalRoutesDao
import com.englizya.local.internalRoutes.InternalRoutesDao
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.RouteRepository


class RouteRepositoryImpl constructor(
    private val routeService: RouteService,
    private val internalRouteDao: InternalRoutesDao,
    private val externalRoutesDao: ExternalRoutesDao,
) : RouteRepository {


    override suspend fun getExternalLines(getForcedOnline: Boolean): Result<List<ExternalRoutes>> =
        kotlin.runCatching {
            if (getForcedOnline) {
                routeService.getExternalLines().also {
                    externalRoutesDao.clearExternalRoutes()
                    externalRoutesDao.insertExternalRoutes(it)
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
                    internalRouteDao.clearInternalRoutes()
                    internalRouteDao.insertInternalRoutes(it)
                    Log.d("DataRemote",it.toString())

                }
            } else {
                getLocalInternalRoute()
            }
        }


    private fun getLocalInternalRoute(): List<InternalRoutes> {
        Log.d("Datalocal",internalRouteDao.getInternalRoutes().toString())

        return internalRouteDao.getInternalRoutes()
    }

    private fun getLocalExternalRoute(): List<ExternalRoutes> {
        Log.d("Datalocal",externalRoutesDao.getExternalRoutes().toString())
        return externalRoutesDao.getExternalRoutes()
    }

}