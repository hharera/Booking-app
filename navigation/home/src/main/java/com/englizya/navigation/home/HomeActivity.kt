package com.englizya.navigation.home

import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.feature.ticket.navigation.home.R
import com.englizya.feature.ticket.navigation.home.databinding.ActivityHomeBinding
import com.englizya.home_screen.HomeViewModel
import com.englyzia.navigation.NavigationAdapter
import com.englyzia.navigation.NavigationItem
import com.englyzia.navigation.NavigationItem.*

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val navigationItemList = arrayListOf(
        PaymentHistory,
        ReportProblem,
        AboutUs,
        TermsAndPolicy,
        UpcomingFeatures,
        SuggestIdea,
        ProfileSettings,
        PaymentCards,
        AppSettings,
    )

    private val homeViewModel: HomeViewModel by viewModels()
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host)

        getExtras()
        setupRecyclerViewAdapter()
        setupBottomNavigation()
        setupListeners()
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            homeViewModel.onNavigationClicked.observe(this) {
                if (it) {
                    binding.root.openDrawer(Gravity.RIGHT)
                }
            }
        }


        binding.back.setOnClickListener {
            binding.root.closeDrawers()
        }
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupRecyclerViewAdapter() {
        val adapter =
            NavigationAdapter(navigationItemList) {
                checkClickItem(it)
            }

        binding.navigationMenu.adapter = adapter
    }

    private fun checkClickItem(item: NavigationItem) {
        when (item) {
            is AppSettings -> {
                navigateToSettings()
            }

            is PaymentCards -> {

            }

            is ProfileSettings -> {

            }

            is TermsAndPolicy -> {

            }

            is PaymentHistory -> {

            }

            is ReportProblem -> {

            }

            is SuggestIdea -> {

            }

            is AboutUs -> {

            }

            is UpcomingFeatures -> {

            }
        }
    }

    private fun navigateToSettings() {
        navController.navigate(R.id.navigation_app_settings)
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