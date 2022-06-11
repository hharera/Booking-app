package com.englyzia.paytabs

import com.englizya.model.model.User
import com.englizya.model.payment.CustomerDetails
import com.englizya.model.payment.FawryInvoice
import com.englizya.model.payment.Invoice
import com.englizya.model.payment.LineItems
import com.englyzia.paytabs.utils.CountryCode.EG
import com.englyzia.paytabs.utils.Currency
import com.englyzia.paytabs.utils.Domain
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import org.joda.time.DateTime
import java.util.*

class PayTabsService {

    companion object {
        fun createFawryInvoice(
            user: User,
            tripName: String,
            amount: Double,
            cartId: Int,
            seatsCount: Int,
        ): FawryInvoice {
            return FawryInvoice(
                "98492",
                tranType = PaymentSdkTransactionType.SALE.name,
                tranClass = PaymentSdkTransactionClass.ECOM.name,
                cartCurrency = Currency.EG,
                cartAmount = amount.toString(),
                cartId = cartId.toString(),
                cartDescription = "حجز $seatsCount مقعد/مثاعد علي $tripName",
                hideShipping = true,
                customerRef = user.username,
                customerDetails = CustomerDetails(
                    user.name,
                    user.phoneNumber.plus("@englizya.com"),
                    user.address,
                    user.address,
                    "Egypt",
                ),
                invoice = Invoice(
                    shippingCharges = 0,
                    extraCharges = 0,
                    extraDiscount = 0,
                    total = amount.toInt(),
                    activationDate = Date().toString(),
                    expiryDate = DateTime.now().plusHours(2).toDate().toString(),
                    dueDate = DateTime.now().plusHours(3).toDate().toString(),
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
                "${Domain.FAWRY_CALLBACK}/$cartId",
                "${Domain.FAWRY_CALLBACK}/$cartId",
            )
        }

        fun createPaymentConfigData(
            user: User,
            tripName: String,
            amount: Double,
            cartId: Int,
        ): PaymentSdkConfigurationDetails {
            return PaymentSdkConfigBuilder(
                "98492",
                "SBJNDG62TW-JD96MMMRDT-HZZTJTWDGN",
                "C7KMBP-9DDG6D-PGNNNR-DDV6TV",
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
                "98492",
                "SBJNDG62TW-JD96MMMRDT-HZZTJTWDGN",
                "C7KMBP-9DDG6D-PGNNNR-DDV6TV",
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