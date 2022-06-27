package com.englizya.repository

import com.englizya.model.model.LineDetails
import com.englizya.model.model.Routes

interface RouteRepository {
    suspend fun getExternalLines(): Result<List<Routes>>

}