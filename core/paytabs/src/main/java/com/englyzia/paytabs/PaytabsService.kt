package com.englyzia.paytabs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.englizya.model.model.User
import com.englyzia.paytabs.utild.CountryCode
import com.englyzia.paytabs.utild.Currency
import com.payment.paymentsdk.PaymentSdkActivity.Companion.startCardPayment
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface

class PaytabsService : AppCompatActivity(), CallbackPaymentInterface {

    //    create
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profileId = "94307"
        val serverKey = "SJJNDG62RM-JDHRGDH6LT-2Z6MDWGW6R"
        val clientLey = "CKKMBP-9DRH6D-HRTDHG-6NKGKQ"
        val locale = PaymentSdkLanguageCode.EN
        val screenTitle = "Test SDK"
        val cartId = "123456"
        val cartDesc = "cart description"
        val currency = Currency.EG
        val amount = 20.0
        val tokeniseType = PaymentSdkTokenise.NONE

        val tokenFormat = PaymentSdkTokenFormat.Hex32Format()
        val billingData = PaymentSdkBillingDetails(
            "Beba",
            countryCode = "EG",
            email = "hassan.harera@gmail.com",
            "Hassan Harera",
            "01062227714",
            "Beni Suef",
            addressLine = "Beni Suef",
            "62611"
        )

        val configData =
            PaymentSdkConfigBuilder(
                profileId,
                serverKey,
                clientLey,
                amount,
                currency
            )
                .setCartDescription(cartDesc)
                .setLanguageCode(locale)
                .setBillingData(billingData)
                .setMerchantCountryCode("EG")
                .setCartId(cartId)
                .setTransactionType(PaymentSdkTransactionType.SALE)
                .showBillingInfo(false)
                .showShippingInfo(false)
                .forceShippingInfo(false)
                .setScreenTitle(screenTitle)
                .build()

        startCardPayment(this, configData, this)
    }

    override fun onError(error: PaymentSdkError) {
        Log.d(TAG, "onError: " + error.msg)
    }

    override fun onPaymentCancel() {
        Log.d(TAG, "onPaymentCancel: ")
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        paymentSdkTransactionDetails.token?.let {
            Log.d(TAG, "onPaymentFinish: $it")
        }
    }

    companion object {
        fun createBillingInfo(user: User): PaymentSdkBillingDetails {
            return PaymentSdkBillingDetails(
                user.address,
                countryCode = CountryCode.EG,
                email = user.uid.plus("@englizya.com"),
                name = user.name,
                user.phoneNumber,
                user.address,
                user.address,
                user.address,
            )
        }
    }
}