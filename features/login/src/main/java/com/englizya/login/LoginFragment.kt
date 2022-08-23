package com.englizya.login

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.login.databinding.FragmentLoginBinding
import com.englizya.navigation.forget_password.ResetPasswordActivity
import com.englizya.navigation.home.HomeActivity
import com.englizya.navigation.signup.SignupActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var bind: FragmentLoginBinding
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginManager: LoginManager
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentLoginBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printHashKey()
        context?.let { FacebookSdk.sdkInitialize(it.applicationContext) };

        callbackManager = CallbackManager.Factory.create()
        facebookLogin()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        // add this line
        callbackManager.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }

    override fun onResume() {
        super.onResume()
        restoreValues()
    }

    private fun restoreValues() {
        loginViewModel.password.value?.let {
            bind.password.setText(it)
        }

        loginViewModel.phoneNumber.value?.let {
            bind.phoneNumber.setText(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        printHashKey()
        setupSignupSpannable()
    }

    private fun setupSignupSpannable() {
        val notHaveAccount = getString(R.string.not_have_an_account)
        val signup = getString(R.string.signup)

        val spannable: Spannable = SpannableString(notHaveAccount.plus(signup))
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            notHaveAccount.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.BLUE),
            notHaveAccount.length,
            signup.length + notHaveAccount.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        bind.signup.text = spannable
    }

    private fun setupObservers() {
        loginViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(exception = it)
        }

        loginViewModel.loginOperationState.observe(viewLifecycleOwner) { state ->
            checkLoginState(state)
        }

        connectionLiveData.observe(viewLifecycleOwner) { state ->
            showInternetSnackBar(bind.root, state)
        }

        loginViewModel.formValidity.observe(viewLifecycleOwner) {
            bind.login.isEnabled = it.formIsValid

            if (it.phoneNumberError != null) {
                bind.textInputLayoutPhoneNumber.error = getString(it.phoneNumberError!!)
            } else {
                bind.textInputLayoutPhoneNumber.error = null
            }

            if (it.passwordError != null) {
                bind.textInputLayoutPassword.error = getString(it.passwordError!!)
            } else {
                bind.textInputLayoutPassword.error = null
            }
        }
    }

    private fun checkLoginState(state: Boolean) {
        if (state) {
            progressToHomeActivity()
        } else {
            showToast(R.string.cannot_login)
        }
    }

    private fun progressToHomeActivity() {
        activity?.apply {
            startActivity(
                Intent(
                    context,
                    HomeActivity::class.java
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
        }
    }

    private fun setupListeners() {
        bind.phoneNumber.afterTextChanged { phoneNumber ->
            loginViewModel.setPhoneNumber(phoneNumber)
        }

        bind.forgetPassword.setOnClickListener {
            goToResetPassword()
        }

        bind.password.afterTextChanged {
            loginViewModel.setPassword(it)
        }
        bind.loginButton.setOnClickListener {
            loginManager.logInWithReadPermissions(
                this,
                Arrays.asList(
                    "email",
                    "public_profile",

                    )
            )
        }


        bind.signup.setOnClickListener {
            gotToSignup()
        }

        bind.login.setOnClickListener {
            loginViewModel.login()
            bind.login.isEnabled = false
        }
    }

    private fun goToResetPassword() {
        activity?.apply {
            startActivity(Intent(context, ResetPasswordActivity::class.java))
        }
    }

    private fun gotToSignup() {
        activity?.apply {
            startActivity(Intent(context, SignupActivity::class.java))
        }
    }

    fun printHashKey() {

        // Add code to print out the key hash
        try {
            val info: PackageInfo = activity?.packageManager?.getPackageInfo(
                "com.android.facebookloginsample",
                PackageManager.GET_SIGNATURES
            )!!
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "KeyHash:", encodeToString(md.digest(), DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    fun facebookLogin() {
        loginManager = LoginManager.getInstance()
        callbackManager = CallbackManager.Factory.create()
        loginManager
            .registerCallback(
                callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        handleFacebookAccessToken(loginResult.accessToken);

                    }

                    override fun onCancel() {
                        Log.v("LoginScreen", "---onCancel")
                    }

                    override fun onError(error: FacebookException) {
                        // here write code when get error
                        Log.v(
                            "LoginScreen", "----onError: "
                                    + error.message
                        )
                    }
                })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
        auth = FirebaseAuth.getInstance()
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    if (user != null) {
                        navigateToSetPassword(user.uid)
                        Log.d(TAG, "UserId : ${user.uid}")
                    }
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    showToast("Authentication Failed")
//                    updateUI(null)
                }
            }
    }

    private fun navigateToSetPassword(uid: String) {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.SET_PASSWORD,
                uid
            )
        )
    }

}