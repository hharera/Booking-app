package com.englizya.complete_user_info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.complete_user_info.databinding.FragmentCompleteUserInfoBinding
import com.englizya.navigation.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompleteUserInfoFragment : BaseFragment() {

    private val completeInfoViewModel: CompleteUserInfoViewModel by viewModel()
    private lateinit var binding: FragmentCompleteUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCompleteUserInfoBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setupListeners()
    }

    private fun setUpObservers() {
        completeInfoViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
        completeInfoViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(exception = it)
        }
        completeInfoViewModel.loginOperationState.observe(viewLifecycleOwner) { state ->
            checkLoginState(state)
        }
        completeInfoViewModel.signupOperationState.observe(viewLifecycleOwner) { state ->
            checkSignupState(state)
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
    private fun checkSignupState(state: Boolean) {
        if (state) {
            completeInfoViewModel.login()
            updateSignupUI()
        } else {
            showToast(R.string.cannot_login)
        }
    }
    private fun updateSignupUI() {
        lifecycleScope.launch(Dispatchers.Main) {
            showDoneDialog()

            delay(1000)

            dismissDoneDialog()

            activity?.finish()
        }
    }
    private fun setupListeners() {
        binding.password.afterTextChanged {
            completeInfoViewModel.setPassword(it)
        }

        binding.name.afterTextChanged {
            completeInfoViewModel.setName(it)
        }
        binding.phone.afterTextChanged {
            completeInfoViewModel.setPhoneNumber(it)
        }

        binding.complete.setOnClickListener {
            if(completeInfoViewModel.validateForm()){
                lifecycleScope.launch {
                    completeInfoViewModel.signup()
                }

                binding.complete.isEnabled = false
            }else{
                showToast(R.string.complete_your_info)
            }

        }
    }

}