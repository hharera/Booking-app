package com.englizya.navigation.home

import android.os.Bundle
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
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
import com.englizya.home_screen.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController


    private val homeViewModel: HomeViewModel by viewModel()
    private val TAG = "HomeActivity"

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
            navController.navigate(R.id.navigation_home, Bundle(), NavOptions.Builder().setLaunchSingleTop(true).build())
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
        val fm: FragmentManager = supportFragmentManager
        for (frag in fm.getFragments()) {
            if (frag.isVisible()) {
                val childFm: FragmentManager = frag.getChildFragmentManager()
                if (childFm.getBackStackEntryCount() > 0) {
                    for (childfragnested in childFm.getFragments()) {
                        val childFmNestManager: FragmentManager =
                            childfragnested.getFragmentManager()!!
                        if (childfragnested.isVisible()) {
                            childFmNestManager.popBackStack()
                            return
                        }
                    }
                }
            }
        }
        super.onBackPressed()    }
}