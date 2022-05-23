package com.englizya.feature.set_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.ui.DoneDialog
import com.englizya.common.utils.navigation.Arguments.PHONE_NUMBER
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils.getUriNavigation
import com.englizya.feature.set_password.databinding.FragmentSetPasswordBinding
import com.englizya.send_otp.SendOtpViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetPasswordFragment : BaseFragment() {

    private val setPasswordViewModel: SetPasswordViewModel by viewModel()
    private val sendOtpViewModel: SendOtpViewModel by sharedViewModel()
    private val doneDialog: DoneDialog by lazy { DoneDialog() }
    private lateinit var bind: FragmentSetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPasswordViewModel.setPhoneNumber(sendOtpViewModel.phoneNumber.value!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSetPasswordBinding.inflate(layoutInflater)
        return bind.root
    }

    private fun restoreValues() {
        bind.password.setText(setPasswordViewModel.password.value)
        bind.name.setText(setPasswordViewModel.name.value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        setPasswordViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        setPasswordViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(exception = it)
        }

        setPasswordViewModel.signupOperationState.observe(viewLifecycleOwner) { state ->
            checkSignupState(state)
        }

        setPasswordViewModel.formValidity.observe(viewLifecycleOwner) {
            bind.pay.isEnabled = it.isValid

            if (it.passwordError != null) {
                bind.textInputLayoutPassword.error = getString(it.passwordError!!)
            } else {
                bind.textInputLayoutPassword.error = null
            }
        }
    }

    private fun checkSignupState(state: Boolean) {
        if (state) {
            updateSignupUI()
        } else {
            showToast(R.string.cannot_login)
        }
    }

    private fun updateSignupUI() {
        lifecycleScope.launch {
            showDoneDialog()

            delay(1000)

            doneDialog.dismiss()

//            navigateToPaymentInfo()
            activity?.finish()
        }
    }

    private fun navigateToPaymentInfo() {
        findNavController().navigate(
            getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.USER_FORM,
                null
            )
        )
    }

    private fun setupListeners() {
        bind.password.afterTextChanged {
            setPasswordViewModel.setPassword(it)
        }

        bind.name.afterTextChanged {
            setPasswordViewModel.setName(it)
        }

        bind.pay.setOnClickListener {
            lifecycleScope.launch {
                setPasswordViewModel.signup()
            }

            bind.pay.isEnabled = false
        }
    }
}