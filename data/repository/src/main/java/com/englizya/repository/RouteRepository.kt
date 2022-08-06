package com.englizya.repository

import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.model.model.Offer
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
     fun getExternalLines(): Flow<Resource<List<ExternalRoutes>>>
     fun getInternalLines(): Flow<Resource<List<InternalRoutes>>>

}