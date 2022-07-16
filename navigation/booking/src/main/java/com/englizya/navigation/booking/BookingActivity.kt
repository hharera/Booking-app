package com.englizya.navigation.booking

import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.englizya.common.base.BaseActivity
import com.englizya.feature.ticket.navigation.booking.R
import com.englizya.feature.ticket.navigation.booking.databinding.ActivityBookingBinding


class BookingActivity : BaseActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(findViewById(R.id.bookingNavHost))
    }

    override fun onBackPressed() {
        if (navController.backQueue.size > 1) {
            navController.popBackStack()
        } else {
            finish()
        }
    }
}





