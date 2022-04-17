package com.englizya.navigation.booking

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.common.base.BaseActivity
import com.englizya.feature.ticket.navigation.booking.R
import com.englizya.feature.ticket.navigation.booking.databinding.ActivityBookingBinding
import com.englyzia.booking.BookingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingActivity : BaseActivity() {

    private lateinit var bind: ActivityBookingBinding
    private lateinit var navController: NavController
    private val bookingViewModel: BookingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.navView)
        setupObservers()
    }

    private fun setupObservers() {

        bookingViewModel.exception.observe(this) {
        }
    }
}