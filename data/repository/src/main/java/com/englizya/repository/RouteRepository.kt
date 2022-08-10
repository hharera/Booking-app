package com.englizya.repository

import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
     fun getExternalLines(forceOnline: Boolean = false): Flow<Resource<List<ExternalRoutes>>>
     fun getInternalLines(forceOnline : Boolean = false): Flow<Resource<List<InternalRoutes>>>

}