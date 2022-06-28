package com.englizya.api

import com.englizya.model.model.LineDetails
import com.englizya.model.model.Routes

interface RouteService {
    suspend fun getExternalLines(): List<Routes>
    suspend fun getInternalLines(): List<Routes>


}