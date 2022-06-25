package com.englizya.navigation.home

import android.os.Bundle
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    companion object {
        const val TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host)
        navController.enableOnBackPressed(true)
        navController.setOnBackPressedDispatcher(onBackPressedDispatcher)

        getExtras()
        setupBottomNavigation()
        disableHomeMenuItem()
    }

    private fun disableHomeMenuItem() {
        binding.bottomNavigation.menu[2].isEnabled = false
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
    }

    private fun setupListeners() {
        binding.home.setOnClickListener {
            checkHome()
            navController.navigate(
                R.id.navigation_home,
                Bundle(),
                NavOptions.Builder().setLaunchSingleTop(true).build()
            )
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    checkHome()
                    navController.navigate(
                        R.id.navigation_home,
                        Bundle(),
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }

                R.id.navigation_profile -> {
                    navController.navigate(
                        R.id.navigation_profile,
                        Bundle(),
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }

                R.id.navigation_booking -> {
                    navController.navigate(
                        R.id.navigation_booking,
                        Bundle(),
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }

                R.id.navigation_map -> {
                    navController.navigate(
                        R.id.navigation_map,
                        Bundle(),
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }

                R.id.navigation_routes -> {
                    navController.navigate(
                        R.id.navigation_routes,
                        Bundle(),
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }
            }
            true
        }
    }

    private fun checkHome() {
        binding.bottomNavigation.menu.forEach {
            it.isChecked = it.itemId == R.id.navigation_home
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

    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                fragment.parentFragmentManager.popBackStack()
            }
        }
        super.onBackPressed()
    }
}