package com.englizya.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.login.databinding.FragmentLoginBinding
import com.englizya.navigation.forget_password.ForgetPasswordActivity
import com.englizya.navigation.home.HomeActivity
import com.englizya.navigation.signup.SignupActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var bind: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getString(Arguments.REDIRECT)?.let { redirect ->
                loginViewModel.setRedirectRouting(redirect)
            }
        }

        changeStatusBarColor(R.color.blue_600)
    }

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
        bind.password.setText(loginViewModel.password.value)
        bind.phoneNumber.setText(loginViewModel.phoneNumber.value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            if (it.phoneNumberError != null) {
                bind.phoneNumber.error = getString(it.phoneNumberError!!)
            } else if (it.passwordError != null) {
                bind.password.error = getString(it.passwordError!!)
            }
        }
    }


    private fun checkLoginState(state: Boolean) {
        if (state) {
            redirect()
        } else {
            showToast(R.string.cannot_login)
        }
    }

    private fun redirect() {
//        TODO: cases to redirect

        activity?.apply {
            startActivity(
                Intent(context, HomeActivity::class.java)
            )
            finish()
        }
        return


        val redirectDestination = loginViewModel.redirectRouting.value

        if (redirectDestination == null) {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.TICKET,
                    null
                )
            )
            return
        }

        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                redirectDestination,
                null
            )
        )
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
            lifecycleScope.launch(Dispatchers.IO) {
                loginViewModel.login()
            }
            bind.login.isEnabled = false
        }
    }

    private fun goToResetPassword() {
        activity?.apply {
            startActivity(Intent(context, ForgetPasswordActivity::class.java))
        }
    }

    private fun gotToSignup() {
        activity?.apply {
            startActivity(Intent(context, SignupActivity::class.java))
        }
    }
}