package com.englizya.repository

import com.englizya.api.WalletService
import com.englizya.model.dto.UserBalance
import com.englizya.model.request.InvoicePaymentOrderRequest
import com.englizya.model.request.PaymentOrderRequest
import com.englizya.model.request.RechargingRequest
import com.englizya.model.response.InvoicePaymentResponse
import com.englizya.model.response.PaymentOrder
import com.englyzia.paytabs.dto.Invoice

interface WalletRepository {
    suspend fun getBalance(token: String): Result<UserBalance>
    suspend fun rechargeBalance(
        token: String,
        rechargingRequest: RechargingRequest
    ): Result<UserBalance>

    suspend fun requestRecharge(token: String, request: PaymentOrderRequest): Result<PaymentOrder>
    suspend fun requestInvoicePayment(request: Invoice): Result<InvoicePaymentResponse>

}

class WalletRepositoryImpl constructor(
    private val walletService: WalletService
) : WalletRepository {

    override suspend fun getBalance(token: String): Result<UserBalance> = kotlin.runCatching {
        walletService.getBalance(token)
    }

    override suspend fun rechargeBalance(
        token: String,
        rechargingRequest: RechargingRequest
    ): Result<UserBalance> = kotlin.runCatching {
        walletService.rechargeBalance(token, rechargingRequest)
    }

    override suspend fun requestRecharge(
        token: String,
        request: PaymentOrderRequest,
    ): Result<PaymentOrder> = kotlin.runCatching {
        walletService.requestRecharge(token)
    }

    override suspend fun requestInvoicePayment(request: Invoice): Result<InvoicePaymentResponse> = kotlin.runCatching {
        walletService.requestInvoicePayment(request)

    }



}