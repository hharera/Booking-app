package com.englizya.navigation.booking

import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import com.englizya.common.base.BaseActivity
import com.englizya.feature.ticket.navigation.booking.databinding.ActivityBookingBinding


class BookingActivity : BaseActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

    }
}





