package com.englyzia.paytabs

import com.englizya.model.model.User
import com.englyzia.paytabs.BuildConfig.*
import com.englyzia.paytabs.dto.CustomerDetails
import com.englyzia.paytabs.dto.Invoice
import com.englyzia.paytabs.dto.InvoiceDetails
import com.englyzia.paytabs.dto.LineItems
import com.englyzia.paytabs.utils.CountryCode.EG
import com.englyzia.paytabs.utils.Currency
import com.englyzia.paytabs.utils.Domain
import com.englyzia.paytabs.utils.PaymentMethod
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import org.joda.time.DateTime

class PayTabsService {

    companion object {
        fun createInvoice(
            user: User,
            tripName: String,
            amount: Double,
            cartId: Int,
            seatsCount: Int,
            paymentMethod: PaymentMethod,
        ): Invoice {
            return Invoice(
                LIVE_PROFILE_ID,
                tranType = PaymentSdkTransactionType.SALE.name,
                tranClass = PaymentSdkTransactionClass.ECOM.name,
                cartCurrency = Currency.EG,
                cartAmount = amount.toString(),
                cartId = cartId.toString(),
                cartDescription = "حجز $seatsCount مقعد/مقاعد علي $tripName",
                hideShipping = true,
                customerRef = user.username,
                customerDetails = CustomerDetails(
                    user.name,
                    user.phoneNumber.plus("@englizya.com"),
                    user.address,
                    user.address,
                    "Egypt",
                ),
                invoice = InvoiceDetails(
                    shippingCharges = 0,
                    extraCharges = 0,
                    extraDiscount = 0,
                    total = amount.toInt(),
                    activationDate = DateTime.now().plusSeconds(3).toString(),
                    expiryDate = DateTime.now().plusHours(2).toString(),
                    dueDate = DateTime.now().plusHours(2).toString(),
                    lineItems = arrayListOf(
                        LineItems(
                            "1",
                            "حجز مقعد/مقاعد علي رحلة".plus(tripName),
                            "englizya.com",
                            amount,
                            1,
                            amount,
                            0,
                            0,
                            0,
                            0,
                            amount
                        )
                    ),
                ),
                "${Domain.INVOICE_CALLBACK}/$cartId",
                "${Domain.INVOICE_CALLBACK}/$cartId",
                paymentMethods = arrayListOf(paymentMethod.name),
            )
        }

        fun createInvoice(
            user: User,
            amount: Double,
            cartId: Int,
            paymentMethod: PaymentMethod,
        ): Invoice {
            return Invoice(
                LIVE_PROFILE_ID,
                tranType = PaymentSdkTransactionType.SALE.name,
                tranClass = PaymentSdkTransactionClass.ECOM.name,
                cartCurrency = Currency.EG,
                cartAmount = amount.toString(),
                cartId = cartId.toString(),
                cartDescription = "شحن محفظة الانجليزية",
                hideShipping = true,
                customerRef = user.username,
                customerDetails = CustomerDetails(
                    user.name,
                    user.phoneNumber.plus("@englizya.com"),
                    user.address,
                    user.address,
                    "Egypt",
                ),
                invoice = InvoiceDetails(
                    shippingCharges = 0,
                    extraCharges = 0,
                    extraDiscount = 0,
                    total = amount.toInt(),
                    activationDate = DateTime.now().plusSeconds(3).toString(),
                    expiryDate = DateTime.now().plusHours(2).toString(),
                    dueDate = DateTime.now().plusHours(2).toString(),
                    lineItems = arrayListOf(
                        LineItems(
                            "1",
                            "شحن محفظة الانجليزية",
                            "englizya.com",
                            amount,
                            1,
                            amount,
                            0,
                            0,
                            0,
                            0,
                            amount
                        )
                    ),
                ),
                "${Domain.INVOICE_CALLBACK}/$cartId",
                "${Domain.INVOICE_CALLBACK}/$cartId",
                paymentMethods = arrayListOf(paymentMethod.name),
            )
        }
        fun createPaymentConfigData(
            user: User,
            tripName: String,
            amount: Double,
            cartId: Int,
        ): PaymentSdkConfigurationDetails {
            return PaymentSdkConfigBuilder(
                LIVE_PROFILE_ID,
                LIVE_SERVER_KEY,
                LIVE_CLIENT_KEY,
                amount,
                Currency.EG
            )
                .setCartId(cartId = cartId.toString())
                .setCartDescription(tripName)
                .setLanguageCode(PaymentSdkLanguageCode.EN)
                .setBillingData(createBillingInfo(user))
                .setMerchantCountryCode(EG)
                .setTransactionType(PaymentSdkTransactionType.SALE)
                .showBillingInfo(false)
                .showShippingInfo(false)
                .forceShippingInfo(false)
                .setScreenTitle("Englizya Payment")
                .build()
        }

        private fun createBillingInfo(user: User): PaymentSdkBillingDetails {
            return PaymentSdkBillingDetails(
                name = user.name,
                countryCode = EG,
                email = user.phoneNumber.plus("@englizya.com"),
                phone = user.phoneNumber,
                addressLine = "Cairo",
                state = "Cairo",
                zip = "123456",
                city = "Cairo",
            )
        }

        fun createPaymentConfigData(
            user: User,
            amount: Double,
            cartId: Int,
        ): PaymentSdkConfigurationDetails {
            return PaymentSdkConfigBuilder(
                LIVE_PROFILE_ID,
                LIVE_SERVER_KEY,
                LIVE_CLIENT_KEY,
                amount,
                Currency.EG
            )
                .setCartId(cartId = cartId.toString())
                .setCartDescription(createCartDescription(amount))
                .setLanguageCode(PaymentSdkLanguageCode.EN)
                .setBillingData(createBillingInfo(user))
                .setMerchantCountryCode(EG)
                .setTransactionType(PaymentSdkTransactionType.SALE)
                .showBillingInfo(false)
                .showShippingInfo(false)
                .forceShippingInfo(false)
                .setScreenTitle("Englizya Payment")
                .build()
        }

        fun createCartDescription(amount: Double): String {
            return "Recharging wallet with $amount EGP"
        }
    }
}