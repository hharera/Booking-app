package com.englizya.signup

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.code.CodeHandler
import com.englizya.common.utils.code.CountryCode
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.send_otp.SendOtpViewModel
import com.englizya.signup.databinding.FragmentSignupBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseFragment() {

    private val signupViewModel: SignupViewModel by viewModel()
    private val sendOtpViewModel: SendOtpViewModel by sharedViewModel()
    private lateinit var bind: FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getString(Arguments.REDIRECT)?.let { redirect ->
                signupViewModel.setRedirectRouting(redirect)
            }
        }

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
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupTermsAndConditions()
    }

    private fun setupTermsAndConditions() {
        val termsAndConditions = getString(R.string.terms_and_conditions)
        val accept = getString(R.string.read_and_accept)

        val spannable: Spannable = SpannableString(accept.plus(termsAndConditions))
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            accept.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.BLUE),
            accept.length,
            accept.length + termsAndConditions.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        bind.termsAndConditionsTV.text = spannable
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
                bind.textInputLayoutPhoneNumber.error = getString(it.phoneNumberError!!)
            } else {
                bind.textInputLayoutPhoneNumber.error = null
            }

            if (it.termsAcceptanceError != null) {
                bind.textInputLayoutPhoneNumber.error = getString(it.termsAcceptanceError!!)
            } else {
                bind.textInputLayoutPhoneNumber.error = null
            }
        }

        signupViewModel.termsAccepted.observe(viewLifecycleOwner) {
            if (it) {
                bind.acceptPolicy.setImageResource(R.drawable.ic_accepted)
            } else {
                bind.acceptPolicy.setImageResource(R.drawable.background_button_accept)
            }
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(bind.root, it)
        }
    }

    private fun setupListeners() {
        bind.phoneNumber.afterTextChanged { phoneNumber ->
            signupViewModel.setPhoneNumber(phoneNumber)
        }

        bind.acceptPolicy.setOnClickListener {
            signupViewModel.whenAcceptedClicked()
        }

        bind.termsAndConditionsTV.setOnClickListener {
            showTermsAndConditions()
        }

        bind.signup.setOnClickListener {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.SEND_OTP,
                    false
                )
            ).also {
                sendOtpViewModel.setPhoneNumber(
                    CodeHandler.appendCode(
                        signupViewModel
                            .phoneNumber
                            .value!!,
                        CountryCode.EgyptianCode
                    )
                )
            }.also {
                sendOtpViewModel.setRedirectToSignupCompletion()
            }

            bind.signup.isEnabled = false
        }
    }

    private fun showTermsAndConditions() {
        TermsAndConditionsDialog(signupViewModel).show(childFragmentManager, "TermsAndConditionsDialog")
    }

}