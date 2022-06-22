package com.englizya.api

import com.englizya.model.model.LineDetails

interface RouteService {
    suspend fun getExternalLines(): List<LineDetails>


}