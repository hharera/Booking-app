package com.englizya.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.navigation.login.LoginActivity
import com.englizya.select_service.SelectServiceActivity
import com.englizya.splash.databinding.ActivitySplashBinding
import com.sarnava.textwriter.TextWriter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModel()
    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)
        startTextAnimation()
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

    private fun checkLoginState(loginState: Boolean) {
        if (loginState) {
            selectService()
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

    private fun selectService() {
        val intent =
            Intent(this@SplashActivity, SelectServiceActivity::class.java)
                .putExtra(Arguments.DESTINATION, Destination.HOME)
                .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) }

        startActivity(intent)
    }

    private fun startTextAnimation() {
        bind.textView2
            .setWidth(12f)
            .setColor(getColor(R.color.blue_500))
            .setConfig(TextWriter.Configuration.INTERMEDIATE)
            .setSizeFactor(24f)
            .setLetterSpacing(25f)
            .setText("ENGLIZYA BUS")
            .apply {
                setPadding(20)
                setListener {
                    checkLogin()
                }
            }
            .startAnimation();
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            splashViewModel.checkLoginState()
        }
    }
}