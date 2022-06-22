package com.englizya.repository

import com.englizya.model.model.LineDetails

interface RouteRepository {
    suspend fun getExternalLines(token :String): Result<List<LineDetails>>

}