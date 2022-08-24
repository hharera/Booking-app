package com.englizya.repository.impl

import androidx.room.withTransaction
import com.englizya.api.RouteService
import com.englizya.local.externalRoutes.ExternalRoutesDao
import com.englizya.local.externalRoutes.ExternalRoutesDatabase
import com.englizya.local.internalRoutes.InternalRoutesDao
import com.englizya.local.internalRoutes.InternalRoutesDatabase
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.RouteRepository
import com.englizya.repository.utils.Resource
import com.englizya.repository.utils.networkBoundResource
import kotlinx.coroutines.flow.*


class RouteRepositoryImpl constructor(
    private val routeService: RouteService,
    private val internalRouteDao: InternalRoutesDao,
    private val externalRoutesDao: ExternalRoutesDao,
    private val externalRoutesDatabase: ExternalRoutesDatabase,
    private val internalRoutesDatabase: InternalRoutesDatabase,
) : RouteRepository {

    override fun getExternalLines(forceOnline: Boolean): Flow<Resource<List<ExternalRoutes>>> =
        networkBoundResource(
            query = {
                externalRoutesDao.getExternalRoutes()
            },
            fetch = {
                routeService.getExternalLines()
            },
            saveFetchResult = { externalRoutes ->
                externalRoutesDatabase.withTransaction {
                    externalRoutesDao.clearExternalRoutes()
                    externalRoutesDao.insertExternalRoutes(externalRoutes)
                }
            },
            shouldFetch = {
                forceOnline
            }
        )


    override fun getInternalLines(forceOnline: Boolean): Flow<Resource<List<InternalRoutes>>> =
        networkBoundResource(
            query = {
                internalRouteDao.getInternalRoutes()
            },
            fetch = {
                routeService.getInternalLines()
            },
            saveFetchResult = { internalRoutes ->
                internalRoutesDatabase.withTransaction {
                    internalRouteDao.clearInternalRoutes()
                    internalRouteDao.insertInternalRoutes(internalRoutes)
                }
            },
            shouldFetch = {
                forceOnline
            }
        )

}