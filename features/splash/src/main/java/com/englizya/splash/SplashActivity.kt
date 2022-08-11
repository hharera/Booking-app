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
import com.englizya.repository.utils.Resource
import com.englizya.splash.databinding.ActivitySplashLongBinding
import com.englizya.splash.databinding.ActivitySplashShortBinding
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.sarnava.textwriter.TextWriter
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

    private var appUpdate: AppUpdateManager? = null
    private val REQUEST_CODE = 100
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
        appUpdate = AppUpdateManagerFactory.create(this)
        userDataStore.setFirstOpenState(false)

        checkUpdate()
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
            handleFailure(exception)
        }
        splashViewModel.user.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
//                    handleLoading(true)
                }
                is Resource.Success -> {
                    Log.d("User", resource.data.toString())
//                    handleLoading(false)
                    resource.data?.let { splashViewModel.updateUserDataStore(it) }
                    goBooking()
                }
                is Resource.Error -> {
                    splashViewModel.checkException(resource.error!!)
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

    override fun onResume() {
        super.onResume()
        inProgressUpdate()

    }

    private fun checkUpdate() {

        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                Log.d("updateInfo", updateInfo.toString())
                appUpdate?.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE
                )

            }
        }
    }

    private fun inProgressUpdate() {
        appUpdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            ) {
                Log.d("updateInfo", updateInfo.toString())

                appUpdate?.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE
                )

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode != RESULT_OK) {
            showToast("You should update the Application")
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
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