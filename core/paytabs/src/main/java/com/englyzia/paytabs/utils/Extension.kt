package com.englyzia.paytabs.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.payment.paymentsdk.PaymentSdkActivity


fun AppCompatActivity.clearHistory() {
    intent.flags.and(Intent.FLAG_ACTIVITY_CLEAR_TOP);
}