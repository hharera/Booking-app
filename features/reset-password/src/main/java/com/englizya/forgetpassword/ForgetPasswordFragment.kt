package com.englizya.forgetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.code.CodeHandler
import com.englizya.common.utils.code.CountryCode
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.forgetpassword.databinding.FragmentForgetPasswordBinding
import com.englizya.send_otp.SendOtpViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private val sendOtpViewModel: SendOtpViewModel by sharedViewModel()
    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        forgetPasswordViewModel.phoneNumberValidity.observe(viewLifecycleOwner) {
            binding.next.isEnabled = (it)
        }
    }


    private fun setupListeners() {
        binding.back.setOnClickListener {
            activity?.finish()
        }

        binding.next.setOnClickListener {
            progressToSendOtp()
        }

        binding.phoneNumber.afterTextChanged {
            forgetPasswordViewModel.setPhoneNumber(it)
        }
    }

    private fun progressToSendOtp() {
        findNavController()
            .navigate(
                NavigationUtils
                    .getUriNavigation(
                        Domain.ENGLIZYA_PAY,
                        Destination.SEND_OTP,
                        false
                    )
            ).also {
                sendOtpViewModel.setPhoneNumber(
                    CodeHandler.appendCode(
                        forgetPasswordViewModel.phoneNumber.value!!,
                        CountryCode.EgyptianCode
                    )
                )
            }
            .also {
                sendOtpViewModel.setRedirectToResetPassword()
            }
    }
}