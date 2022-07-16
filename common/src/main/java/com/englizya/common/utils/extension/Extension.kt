package com.englizya.common.utils.extension

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

fun AppCompatActivity.clearHistory() {
    intent.flags.and(Intent.FLAG_ACTIVITY_CLEAR_TOP)
}

fun FragmentActivity.clearHistory() {
    intent.flags.and(Intent.FLAG_ACTIVITY_CLEAR_TOP)
}