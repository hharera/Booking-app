package com.englizya.repository

import com.englizya.model.model.Station

interface StationRepository {
    suspend fun getAllStations(): Result<List<Station>>
}