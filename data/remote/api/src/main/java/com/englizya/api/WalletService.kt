package com.englizya.api

import com.englizya.api.utils.Header.BEARER
import com.englizya.api.utils.RequestParam.TRA_REF
import com.englizya.api.utils.Routing
import com.englizya.model.dto.UserBalance
import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.RechargingRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englyzia.paytabs.dto.Invoice
import com.google.common.net.HttpHeaders
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.util.*

interface WalletService {
    suspend fun getBalance(token: String): UserBalance
    suspend fun requestRecharge(token: String): PaymentOrder
    suspend fun requestInvoicePayment(request: Invoice): InvoicePaymentResponse

    suspend fun rechargeBalance(token: String, request: RechargingRequest): UserBalance
}

class WalletServiceImpl constructor(
    private val client: HttpClient
) : WalletService {

    override suspend fun requestRecharge(token: String): PaymentOrder =
        client.post(Routing.REQUEST_RECHARGE) {
            headers.append(HttpHeaders.AUTHORIZATION, "$BEARER $token")
        }.body()

    @OptIn(InternalAPI::class)
    override suspend fun requestInvoicePayment(request: Invoice): InvoicePaymentResponse {
        return client.post(Routing.REQUEST_INVOICE_PAYMENT) {
            contentType(ContentType.Application.Json)
            body = request
            headers.append(
                io.ktor.http.HttpHeaders.Authorization,
                "SRJNDG62ZD-JD96MLGDLH-NNNTM9WHGN"
            )
        }.body()
    }


    override suspend fun rechargeBalance(token: String, request: RechargingRequest): UserBalance =
        client.post(Routing.RECHARGE_BALANCE) {
            parameter(TRA_REF, request.transactionRef)
            headers {
                append(HttpHeaders.AUTHORIZATION, "$BEARER $token")
            }
        }.body()

    override suspend fun getBalance(token: String): UserBalance =
        client.get(Routing.GET_BALANCE) {
            headers {
                append(HttpHeaders.AUTHORIZATION, "$BEARER $token")
            }
        }.body()

}