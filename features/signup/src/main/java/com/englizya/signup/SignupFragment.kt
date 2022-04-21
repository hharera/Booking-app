package com.englizya.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.signup.databinding.FragmentSignupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseFragment() {

    private val signupViewModel: SignupViewModel by viewModel()
    private lateinit var bind: FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getString(Arguments.REDIRECT)?.let { redirect ->
                signupViewModel.setRedirectRouting(redirect)
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
        bind = FragmentSignupBinding.inflate(layoutInflater)

        return bind.root
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        signupViewModel.phoneNumber.value?.let {
            bind.phoneNumber.setText(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        signupViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        signupViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(exception = it)
        }

        signupViewModel.formValidity.observe(viewLifecycleOwner) {
            bind.signup.isEnabled = it.isValid

            if (it.phoneNumberError != null) {
                bind.phoneNumber.error = getString(it.phoneNumberError!!)
            }
        }
    }

    private fun setupListeners() {
        bind.phoneNumber.afterTextChanged { phoneNumber ->
            signupViewModel.setPhoneNumber(phoneNumber)
        }

        bind.signup.setOnClickListener {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.SEND_OTP,
                    signupViewModel.phoneNumber.value,
                )
            )

            bind.signup.isEnabled = false
        }
    }

}