package com.englizya.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.forgetpassword.databinding.FragmentResetPasswordBinding
import com.englizya.send_otp.SendOtpViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : BaseFragment() {

    private val resetPasswordViewModel: ResetPasswordViewModel by viewModel()
    private val sendOtpViewModel: SendOtpViewModel by sharedViewModel()
    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.confirm.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                resetPasswordViewModel.resetPassword()
            }
        }

        binding.password.afterTextChanged {
            resetPasswordViewModel.setPassword(it)
        }

        binding.confirmPassword.afterTextChanged {
            resetPasswordViewModel.setConfirmPassword(it)
        }
    }

    private fun setupObservers() {
        resetPasswordViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.confirm.isEnabled = it
        }

        resetPasswordViewModel.operationSuccess.observe(viewLifecycleOwner) {
            if (it) {
                activity?.finish()
            }
        }
    }
}