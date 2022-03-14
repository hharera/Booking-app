package com.englizya.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.login.LoginFragment
import com.englizya.navigation.TicketActivity
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

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(500)
            splashViewModel.checkLoginState()
        }

        splashViewModel.loginState.observe(this) { loginState ->
            checkLoginState(loginState)
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
            Intent(this@SplashActivity, LoginFragment::class.java)
                .putExtra(
                    Arguments.DESTINATION,
                    Destination.LOGIN
                )
        startActivity(intent)
        finish()
    }

    private fun goHome() {
        lifecycleScope.launch {
            val intent = Intent(
                this@SplashActivity,
                TicketActivity::class.java
            ).putExtra(Arguments.DESTINATION, Destination.TICKET)
            //TODO : Set animation between activities
            startActivity(intent)
            finish()
        }
    }
}