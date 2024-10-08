package com.englizya.select_service

import android.content.Intent
import android.os.Bundle
import com.englizya.common.base.BaseActivity
import com.englizya.common.base.BaseFragment
import com.englizya.navigation.home.HomeActivity
import com.englizya.select_service.databinding.ActivitySelectServiceBinding

class SelectServiceActivity : BaseActivity() {

    private lateinit var binding: ActivitySelectServiceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBarColor(R.color.grey_100)
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
    }

    private fun setupListeners() {
        binding.shortTransportationService.setOnClickListener {
            progressToHomeActivity()
        }

        binding.longTransportationService.setOnClickListener {
//            progressToBookingActivity()
        }
    }

//    private fun progressToBookingActivity() {
//        val intent = Intent(
//            this,
//            BookingActivity::class.java
//        )
//
//        startActivity(intent)
//    }

    private fun progressToHomeActivity() {
        val intent = Intent(
            this,
            HomeActivity::class.java
        )

        startActivity(intent)
    }

}