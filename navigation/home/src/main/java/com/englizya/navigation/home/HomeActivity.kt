package com.englizya.navigation.home

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
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

        navController = Navigation.findNavController(this, R.id.navView)

        getExtras()
        setupRecyclerViewAdapter()
        setupBottomNavigation()
        setupListeners()
    }

    private fun setupListeners() {

    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(bind.bottomNavigation, navController)
    }

    private fun setupRecyclerViewAdapter() {
        val adapter =
            NavigationAdapter(
                arrayListOf(
                    NavigationItem.PaymentHistory,
                    NavigationItem.ReportProblem,
                    NavigationItem.AboutUs,
                    NavigationItem.TermsAndPolicy,
                    NavigationItem.UpcomingFeatures,
                    NavigationItem.SuggestIdea,
                    NavigationItem.ProfileSettings,
                    NavigationItem.PaymentCards,
                    NavigationItem.AppSettings,
                )
            ) {
                checkClickItem(it)
            }

        bind.navigationMenu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.navigationMenu.adapter = adapter
    }

    private fun checkClickItem(item: NavigationItem) {
        Log.d(TAG, "checkClickItem: $item")

        when (item) {
            is NavigationItem.AppSettings -> {

            }

            is NavigationItem.PaymentCards -> {

            }

            is NavigationItem.ProfileSettings -> {

            }

            is NavigationItem.TermsAndPolicy -> {

            }

            is NavigationItem.PaymentHistory -> {

            }

            is NavigationItem.ReportProblem -> {

            }

            is NavigationItem.SuggestIdea -> {

            }

            is NavigationItem.AboutUs -> {

            }

            is NavigationItem.UpcomingFeatures -> {

            }
        }
    }

    private fun getExtras() {
        intent?.extras?.getString(Arguments.DESTINATION)?.let {
            navController.navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    it,
                    Destination.TICKET
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }
}