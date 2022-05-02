package com.englyzia.paytabs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.englizya.model.model.User
import com.englyzia.paytabs.utils.CountryCode.EG
import com.englyzia.paytabs.utils.Currency
import com.payment.paymentsdk.PaymentSdkActivity.Companion.startCardPayment
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface

class PayTabsService {

//    //    create
//    private val TAG = "MainActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val profileId = "94307"
//        val serverKey = "SJJNDG62RM-JDHRGDH6LT-2Z6MDWGW6R"
//        val clientLey = "CKKMBP-9DRH6D-HRTDHG-6NKGKQ"
//        val locale = PaymentSdkLanguageCode.EN
//        val screenTitle = "Test SDK"
//        val cartId = "123456"
//        val cartDesc = "cart description"
//        val currency = Currency.EG
//        val amount = 20.0
//        val tokeniseType = PaymentSdkTokenise.NONE
//
//        val tokenFormat = PaymentSdkTokenFormat.Hex32Format()
//        val billingData = PaymentSdkBillingDetails(
//            "Beba",
//            countryCode = "EG",
//            email = "hassan.harera@gmail.com",
//            "Hassan Harera",
//            "01062227714",
//            "Beni Suef",
//            addressLine = "Beni Suef",
//            "62611"
//        )
//
//        val configData =
//            PaymentSdkConfigBuilder(
//                profileId,
//                serverKey,
//                clientLey,
//                amount,
//                currency
//            )
//                .setCartDescription(cartDesc)
//                .setLanguageCode(locale)
//                .setBillingData(billingData)
//                .setMerchantCountryCode("EG")
//                .setCartId(cartId)
//                .setTransactionType(PaymentSdkTransactionType.SALE)
//                .showBillingInfo(false)
//                .showShippingInfo(false)
//                .forceShippingInfo(false)
//                .setScreenTitle(screenTitle)
//                .build()
//
//        startCardPayment(this, configData, this)
//    }
//
//    override fun onError(error: PaymentSdkError) {
//        Log.d(TAG, "onError: " + error.msg)
//    }
//
//    override fun onPaymentCancel() {
//        Log.d(TAG, "onPaymentCancel: ")
//    }
//
//    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
//        paymentSdkTransactionDetails.token?.let {
//            Log.d(TAG, "onPaymentFinish: $it")
//        }
//    }

    companion object {
        fun createPaymentConfigData(
            user: User,
            tripName: String,
            amount: Double,
            cartId : Int,
        ): PaymentSdkConfigurationDetails {
            return PaymentSdkConfigBuilder(
                "94307",
                "SJJNDG62RM-JDHRGDH6LT-2Z6MDWGW6R",
                "CKKMBP-9DRH6D-HRTDHG-6NKGKQ",
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
    }
}