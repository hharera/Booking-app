package com.englyzia.paytabs

import com.englizya.model.model.User
import com.englizya.model.payment.CustomerDetails
import com.englizya.model.payment.FawryInvoice
import com.englizya.model.payment.Invoice
import com.englizya.model.payment.LineItems
import com.englyzia.paytabs.utils.Currency
import com.englyzia.paytabs.utils.Domain
import com.google.gson.Gson
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionClass
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionType
import org.joda.time.DateTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun `test to convert invoice to json`() {
        val user = User(
            uid = "test",
            name = "test",
            username = "test",
            password = "test",
            phoneNumber = "+201062227714",
            address = null,
            walletOtp = "123456",
        )

        FawryInvoice(
            "98492",
            tranType = PaymentSdkTransactionType.SALE.name,
            tranClass = PaymentSdkTransactionClass.ECOM.name,
            cartCurrency = Currency.EG,
            cartAmount = 15.toString(),
            cartId = "cart12345",
            cartDescription = "حجز 5 مقعد/مثاعد علي ",
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
                total = 15,
                activationDate = DateTime.now().toString(),
                expiryDate = DateTime.now().plusHours(2).toDate().toString(),
                dueDate = DateTime.now().plusHours(3).toDate().toString(),
                lineItems = arrayListOf(
                    LineItems(
                        "1",
                        "حجز مقعد/مقاعد علي رحلة",
                        "englizya.com",
                        15.0,
                        1,
                        15.0,
                        0,
                        0,
                        0,
                        0,
                        15.0
                    )
                ),
            ),
            "${Domain.FAWRY_CALLBACK}/cart123",
            "${Domain.FAWRY_CALLBACK}/cart123",
        ).let{
            println(Gson().toJson(it))
        }
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test cart description string`() {
        val seatsCount = 5
        val tripName = "رحلة "
        println("حجز $seatsCount مقعد/مثاعد علي $tripName")
    }
}