package com.englizya.model.request

import com.google.gson.Gson
import org.junit.Test


class PaymentOrderRequestTest {


    @Test
    fun `test to serialize payment order request`(): Unit {
        Gson().toJson(PaymentOrderRequest(20.0)).let {
            println(it)
        }
    }

}