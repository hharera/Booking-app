package com.englizya.api

import com.englizya.model.model.Branch

interface StationService {
    suspend fun getAllStations(): List<Branch>
}