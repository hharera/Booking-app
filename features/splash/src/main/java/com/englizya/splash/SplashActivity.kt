package com.englizya.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.datastore.UserDataStore
import com.englizya.navigation.home.HomeActivity
import com.englizya.navigation.login.LoginActivity
import com.englizya.splash.databinding.ActivitySplashLongBinding
import com.englizya.splash.databinding.ActivitySplashShortBinding
import com.sarnava.textwriter.TextWriter
import io.ktor.client.features.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    companion object {
        private const val TAG = "SplashActivity"
    }

    private lateinit var splashLongBinding: ActivitySplashLongBinding
    private lateinit var splashShortBinding: ActivitySplashShortBinding

    private val splashViewModel: SplashViewModel by viewModel()

    private val userDataStore: UserDataStore by inject()
    private var firstOpenState: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firstOpenState = userDataStore.getFirstOpenState()
        if (firstOpenState) {
            splashLongBinding = ActivitySplashLongBinding.inflate(layoutInflater)
            setContentView(splashLongBinding.root)
        } else {
            splashShortBinding = ActivitySplashShortBinding.inflate(layoutInflater)
            setContentView(splashShortBinding.root)
        }

        updateUI()
        setupObservers()
        userDataStore.setFirstOpenState(false)
    }

    private fun updateUI() {
        if (firstOpenState) {
            updateLongSplashUI()
        } else {
            updateShortSplashUI()
        }
    }

    private fun updateLongSplashUI() {
        startTextAnimation()
    }

    private fun updateShortSplashUI() {
        setVersionName()
        setupAnimation()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
            checkLogin()
        }
    }

    private fun setupAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.animation_activity_splash)
        splashShortBinding.appIcon.startAnimation(animation)
        splashShortBinding.appName.startAnimation(animation)
    }

    private fun setVersionName() {
        packageManager.getPackageInfo(packageName, 0).versionName?.let {
            splashShortBinding.versionText.text = "Version $it"
        }
    }

    private fun setupObservers() {
        splashViewModel.loginState.observe(this) { loginState ->
            checkLoginState(loginState)
        }

        splashViewModel.error.observe(this) { exception ->
            when (exception) {
                is ClientRequestException -> {
                    when (exception.response.status) {
                        HttpStatusCode.Forbidden -> {
                            goLogin()
                        }

                        else -> {
                            goBooking()
                        }
                    }
                }

                is HttpRequestTimeoutException -> {
                    showInternetSnackBar(window.decorView, false)
                    goBooking()
                }
            }
        }
    }

    private fun checkLoginState(loginState: Boolean) {
        if (loginState) {
            goBooking()
        } else {
            goLogin()
        }
    }

    private fun goLogin() {
        Log.d(TAG, "goLogin: ")
        Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(Arguments.DESTINATION, Destination.LOGIN)
        }.let { intent ->
            startActivity(intent)
        }
    }

    private fun goBooking() {
        Intent(this, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }.let { intent ->
            startActivity(intent)
        }
    }

    private fun startTextAnimation() {
        splashLongBinding.appName
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
        splashViewModel.checkLoginState()
    }
}