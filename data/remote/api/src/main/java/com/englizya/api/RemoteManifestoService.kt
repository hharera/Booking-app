package com.englizya.api

interface RemoteManifestoService {
    suspend fun getManifesto(): ManifestoDetails
    suspend fun getShiftReport(endShiftRequest: EndShiftRequest): ShiftReportResponse
}