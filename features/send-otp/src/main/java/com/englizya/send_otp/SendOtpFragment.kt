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

class SendOtpFragment : BaseFragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val sendOtpViewMode: SendOtpViewModel by viewModels()
    private lateinit var bind: FragmentSendOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPhoneNumberArgument()
    }

    private fun getPhoneNumberArgument() {
        arguments?.let {
            it.getString(PHONE_NUMBER)?.let { phoneNumber ->
                sendOtpViewMode.setPhoneNumber(appendCode(phoneNumber, CountryCode.EgyptianCode))
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
        sendOtpViewMode.code.value?.let {
            bind.otpGrid.children.forEachIndexed { index, view ->
                (view as TextView).text = it[index].toString()
            }
        }
    }

    private fun setupNumberListeners() {
        bind.numbersGrid.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> sendOtpViewMode.putCharacter("0")
                    R.id.one -> sendOtpViewMode.putCharacter("1")
                    R.id.two -> sendOtpViewMode.putCharacter("2")
                    R.id.three -> sendOtpViewMode.putCharacter("3")
                    R.id.four -> sendOtpViewMode.putCharacter("4")
                    R.id.five -> sendOtpViewMode.putCharacter("5")
                    R.id.six -> sendOtpViewMode.putCharacter("6")
                    R.id.seven -> sendOtpViewMode.putCharacter("7")
                    R.id.eight -> sendOtpViewMode.putCharacter("8")
                    R.id.nine -> sendOtpViewMode.putCharacter("9")
                    R.id.remove -> sendOtpViewMode.removeCharacter()
                }
            }
        }
    }

    private fun setupListeners() {
        bind.next.setOnClickListener {
            lifecycleScope.launch {
                sendOtpViewMode.signup()
            }
        }
    }

    private fun setupObservers() {
        observeOperation()

        sendOtpViewMode.phoneNumber.observe(viewLifecycleOwner) {
            sendVerificationCode()
        }

        sendOtpViewMode.code.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                sendOtpViewMode.checkCodeValidity()
            }
        }

        sendOtpViewMode.codeValidity.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                sendOtpViewMode.signup()
            }
        }

        sendOtpViewMode.verificationCode.observe(viewLifecycleOwner) { verificationCode ->
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
            .setPhoneNumber(sendOtpViewMode.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(sendOtpViewMode.getVerificationCallBack())
            .setForceResendingToken(sendOtpViewMode.refreshToken.value!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun observeOperation() {
        sendOtpViewMode.verificationState.observe(viewLifecycleOwner) {
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
            .setPhoneNumber(sendOtpViewMode.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(sendOtpViewMode.getVerificationCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}