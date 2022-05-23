package com.englizya.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.login.databinding.FragmentLoginBinding
import com.englizya.navigation.forget_password.ResetPasswordActivity
import com.englizya.navigation.signup.SignupActivity
import com.englizya.select_service.SelectServiceActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var bind: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentLoginBinding.inflate(layoutInflater)
        return bind.root
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
                    SelectServiceActivity::class.java
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