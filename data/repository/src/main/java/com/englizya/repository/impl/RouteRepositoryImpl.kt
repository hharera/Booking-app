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
//import com.englizya.repository.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow


class RouteRepositoryImpl constructor(
    private val routeService: RouteService,
    private val internalRouteDao: InternalRoutesDao,
    private val externalRoutesDao: ExternalRoutesDao,
    private val externalRoutesDatabase: ExternalRoutesDatabase,
    private val internalRoutesDatabase: InternalRoutesDatabase,

    ) : RouteRepository {


//    override suspend fun getExternalLines(getForcedOnline: Boolean): Result<List<ExternalRoutes>> =
//        kotlin.runCatching {
//            if (getForcedOnline) {
//                routeService.getExternalLines().also {
//                    externalRoutesDao.clearExternalRoutes()
//                    externalRoutesDao.insertExternalRoutes(it)
//                    Log.d("DataRemote",it.toString())
//                }
//            } else {
//                getLocalExternalRoute()
//            }
//        }


//    override suspend fun getInternalLines(getForcedOnline: Boolean): Result<List<InternalRoutes>> =
//        kotlin.runCatching {
//            if (getForcedOnline) {
//                routeService.getInternalLines().also {
//                    internalRouteDao.clearInternalRoutes()
//                    internalRouteDao.insertInternalRoutes(it)
//                    Log.d("DataRemote",it.toString())
//
//                }
//            } else {
//                getLocalInternalRoute()
//            }
//        }


//    private fun getLocalInternalRoute(): List<InternalRoutes> {
//        Log.d("Datalocal",internalRouteDao.getInternalRoutes().toString())
//
//        return internalRouteDao.getInternalRoutes()
//    }
//
//    private fun getLocalExternalRoute(): List<ExternalRoutes> {
//        Log.d("Datalocal",externalRoutesDao.getExternalRoutes().toString())
//        return externalRoutesDao.getExternalRoutes()
//    }

    override fun getExternalLines(): Flow<Resource<List<ExternalRoutes>>> = networkBoundResource(
        query =
        {
            externalRoutesDao.getExternalRoutes()
        },
        fetch =
        {
            routeService.getExternalLines()
        },
        saveFetchResult =
        { externalRoutes ->
            externalRoutesDatabase.withTransaction {
                externalRoutesDao.clearExternalRoutes()
                externalRoutesDao.insertExternalRoutes(externalRoutes)
            }


        })


    override fun getInternalLines(): Flow<Resource<List<InternalRoutes>>> = networkBoundResource(
        query =
        {
            internalRouteDao.getInternalRoutes()
        },
        fetch =
        {
            routeService.getInternalLines()
        },
        saveFetchResult =
        { internalRoutes ->
            internalRoutesDatabase.withTransaction {
                internalRouteDao.clearInternalRoutes()
                internalRouteDao.insertInternalRoutes(internalRoutes)
            }


        })

}