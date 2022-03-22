package com.englizya.navigation.booking

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.common.base.BaseActivity
import com.englizya.client.ticket.navigation.booking.R
import com.englizya.client.ticket.navigation.booking.databinding.ActivityBookingBinding

//TODO extend from base activity
class BookingActivity : BaseActivity() {

    private lateinit var bind: ActivityBookingBinding
    private lateinit var navController: NavController
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.nav_host)
    }
}