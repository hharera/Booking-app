package com.englizya.send_otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.code.CodeHandler.appendCode
import com.englizya.common.utils.code.CountryCode
import com.englizya.common.utils.navigation.Arguments.PHONE_NUMBER
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.send_otp.databinding.FragmentSendOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConfirmLoginFragment : BaseFragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val confirmLoginViewMode: ConfirmLoginViewModel by viewModels()
    private lateinit var bind: FragmentSendOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPhoneNumberArgument()
    }

    private fun getPhoneNumberArgument() {
        arguments?.let {
            it.getString(PHONE_NUMBER)?.let { phoneNumber ->
                confirmLoginViewMode.setPhoneNumber(appendCode(phoneNumber, CountryCode.EgyptianCode))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSendOtpBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        confirmLoginViewMode.code.observe(viewLifecycleOwner) {
            bind.otpGrid.children.forEachIndexed { index, view ->
                (view as TextView).text = it[index].toString()
            }
        }
    }

    private fun setupNumberListeners() {
        bind.numbersGrid.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> confirmLoginViewMode.putCharacter("0")
                    R.id.one -> confirmLoginViewMode.putCharacter("1")
                    R.id.two -> confirmLoginViewMode.putCharacter("2")
                    R.id.three -> confirmLoginViewMode.putCharacter("3")
                    R.id.four -> confirmLoginViewMode.putCharacter("4")
                    R.id.five -> confirmLoginViewMode.putCharacter("5")
                    R.id.six -> confirmLoginViewMode.putCharacter("6")
                    R.id.seven -> confirmLoginViewMode.putCharacter("7")
                    R.id.eight -> confirmLoginViewMode.putCharacter("8")
                    R.id.nine -> confirmLoginViewMode.putCharacter("9")
                    R.id.remove -> confirmLoginViewMode.removeCharacter()
                }
            }
        }
    }

    private fun setupListeners() {
        bind.next.setOnClickListener {
            lifecycleScope.launch {
                confirmLoginViewMode.signup()
            }
        }
    }

    private fun setupObservers() {
        observeOperation()

        confirmLoginViewMode.phoneNumber.observe(viewLifecycleOwner) {
            sendVerificationCode()
        }

        confirmLoginViewMode.code.observe(viewLifecycleOwner) {
            confirmLoginViewMode.checkCodeValidity()
        }

        confirmLoginViewMode.codeValidity.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                confirmLoginViewMode.signup()
            }
        }

        confirmLoginViewMode.verificationCode.observe(viewLifecycleOwner) { verificationCode ->
            promptCode()
        }
    }

    private fun promptCode() {
        setupNumberListeners()
        setupResendCodeListener()
    }

    private fun setupResendCodeListener() {
        bind.resendCode.setOnClickListener {
            resendCode()
        }
    }

    private fun resendCode() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(confirmLoginViewMode.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(confirmLoginViewMode.getVerificationCallBack())
            .setForceResendingToken(confirmLoginViewMode.refreshToken.value!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun observeOperation() {
        confirmLoginViewMode.verificationState.observe(viewLifecycleOwner) {
            completeUserInfo()
        }
    }

    private fun completeUserInfo() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.USER_FORM
            )
        )
    }

    private fun sendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(confirmLoginViewMode.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(confirmLoginViewMode.getVerificationCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}