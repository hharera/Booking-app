package com.englizya.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.login.databinding.FragmentLoginBinding
import com.englizya.navigation.forget_password.ResetPasswordActivity
import com.englizya.navigation.home.HomeActivity
import com.englizya.navigation.signup.SignupActivity
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var bind: FragmentLoginBinding
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginManager: LoginManager

    private val EMAIL = "email"


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
        context?.let { FacebookSdk.sdkInitialize(it.applicationContext) };

        callbackManager = CallbackManager.Factory.create()
        loginViewModel.facebookLogin()
//        bind.loginButton.setReadPermissions(Arrays.asList(EMAIL));
//        bind.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                // App code
//            }
//
//            override fun onSuccess(result: LoginResult?) {
//                result?.accessToken?.userId?.let { Log.d("Login", it) }
//            }
//        })
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
}