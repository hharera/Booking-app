package com.englizya.navigation.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.client.ticket.navigation.home.R
import com.englizya.client.ticket.navigation.home.databinding.ActivityHomeBinding
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils

class HomeActivity : BaseActivity() {

    private lateinit var bind: ActivityHomeBinding
    private lateinit var navController: NavController
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.nav_host)

        getExtras()
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter() {
        val adapter: NavigationAdapter =
            NavigationAdapter(
                arrayListOf(
                    NavigationItem.PaymentHistory,
                )
            ) {

            }
        bind.navigationMenu.adapter = adapter
    }

    private fun getExtras() {
        intent?.extras?.getString(Arguments.DESTINATION)?.let {
            navController.navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, it, Destination.TICKET))
        }
    }

    override fun onResume() {
        super.onResume()
    }
}