package com.harera.user_tickets

import android.os.Bundle
import androidx.navigation.NavController
import com.englizya.common.base.BaseActivity
import com.harera.user_tickets.databinding.ActivityUserTicketsBinding

class UserTicketsActivity : BaseActivity() {

    private lateinit var binding: ActivityUserTicketsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTicketsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
    }
}