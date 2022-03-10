package com.englizya.repository

interface ManifestoRepository {
    suspend fun getManifesto(): Result<ManifestoDetails>
    suspend fun getShiftReport(endShiftRequest: EndShiftRequest) : Result<ShiftReportResponse>
}