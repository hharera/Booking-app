package com.englizya.api

import com.englizya.model.model.Station

interface StationService {
    suspend fun getAllStations(): List<Station>
}