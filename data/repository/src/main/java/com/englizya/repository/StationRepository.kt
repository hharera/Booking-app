package com.englizya.repository

import com.englizya.model.model.Branch

interface StationRepository {
    suspend fun getAllStations(): Result<List<Branch>>
}