package com.englizya.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.navigation.home.HomeActivity
import com.englizya.navigation.login.LoginActivity
import com.englizya.navigation.ticket.TicketActivity
import com.englizya.splash.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        window?.statusBarColor = resources.getColor(R.color.splash_status_color)
    }

    override fun onStart() {
        super.onStart()

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        splashViewModel.loginState.observe(this) { loginState ->
            checkLoginState(loginState)
        }
    }

    private fun setupListeners() {
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(2500)
            splashViewModel.checkLoginState()
        }
    }

    private fun checkLoginState(loginState: Boolean) {
        if (loginState) {
            goHome()
        } else {
            goLogin()
        }
    }

    private fun goLogin() {
        val intent =
            Intent(this@SplashActivity, LoginActivity::class.java)
                .putExtra(
                    Arguments.DESTINATION,
                    Destination.LOGIN
                )
        startActivity(intent)
        finish()
    }

    private fun goHome() {
        val intent =
            Intent(this@SplashActivity, HomeActivity::class.java)
                .putExtra(Arguments.DESTINATION, Destination.TICKET)
                .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) }

        startActivity(intent)
    }
}