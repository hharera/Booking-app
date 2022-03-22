package com.englizya.send_otp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.code.CodeHandler.appendCode
import com.englizya.common.utils.code.CountryCode
import com.englizya.common.utils.navigation.Arguments.PASSWORD
import com.englizya.common.utils.navigation.Arguments.PHONE_NUMBER
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.send_otp.databinding.FragmentSendOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SendOtpFragment : BaseFragment() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private lateinit var bind: FragmentSendOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPhoneNumberArgument()
    }

    private fun getPhoneNumberArgument() {
        arguments?.also {
            it.getString(PHONE_NUMBER)?.let { phoneNumber ->
                Log.d(TAG, "getPhoneNumberArgument: $phoneNumber")
                sendOtpViewModel.setPhoneNumber(appendCode(phoneNumber, CountryCode.EgyptianCode))
            }

            it.getString(PASSWORD)?.let { password ->
                sendOtpViewModel.setPassword(password)
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
        sendOtpViewModel.code.value?.let {
            bind.otpGrid.children.forEachIndexed { index, view ->
                (view as TextView).text = it[index].toString()
            }
        }
    }

    private fun setupNumberListeners() {
        bind.zero.setOnClickListener {
            sendOtpViewModel.putCharacter("0")
        }
        bind.one.setOnClickListener {
            sendOtpViewModel.putCharacter("1")
        }
        bind.two.setOnClickListener {
            sendOtpViewModel.putCharacter("2")
        }
        bind.three.setOnClickListener {
            sendOtpViewModel.putCharacter("3")
        }
        bind.four.setOnClickListener {
            sendOtpViewModel.putCharacter("4")
        }
        bind.five.setOnClickListener {
            sendOtpViewModel.putCharacter("5")
        }
        bind.six.setOnClickListener {
            sendOtpViewModel.putCharacter("6")
        }
        bind.seven.setOnClickListener {
            sendOtpViewModel.putCharacter("7")
        }
        bind.eight.setOnClickListener {
            sendOtpViewModel.putCharacter("8")
        }
        bind.nine.setOnClickListener {
            sendOtpViewModel.putCharacter("9")
        }
        bind.delete.setOnClickListener {
            sendOtpViewModel.removeCharacter()
        }
    }

    private fun setupListeners() {
        bind.next.setOnClickListener {
            sendOtpViewModel.signup()
        }
    }

    private fun setupObservers() {
        observeOperation()

        sendOtpViewModel.phoneNumber.observe(viewLifecycleOwner) {
            updatePhoneNumberUI(it)
            sendVerificationCode()
        }

        sendOtpViewModel.code.observe(viewLifecycleOwner) {
            updateCodeUI(it)
            Log.d(TAG, "setupObservers: $it")
        }

        sendOtpViewModel.codeValidity.observe(viewLifecycleOwner) {
            bind.next.isEnabled = it
        }

        sendOtpViewModel.verificationCode.observe(viewLifecycleOwner) { verificationCode ->
            promptCode()
        }
    }

    private fun updateCodeUI(code: String) {
        for (index in 0..5) {
            val char = code.getOrNull(index)
            val str = char?.toString() ?: ""
            when (index) {
                0 -> bind.code1.text = str
                1 -> bind.code2.text = str
                2 -> bind.code3.text = str
                3 -> bind.code4.text = str
                4 -> bind.code5.text = str
                5 -> bind.code6.text = str
            }
        }
    }

    private fun updatePhoneNumberUI(phoneNumber: String) {
        Log.d(TAG, "updateUI: $phoneNumber")
        bind.enterConfirmation.setText(getString(R.string.sending_otp_message, phoneNumber))
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
            .setPhoneNumber(sendOtpViewModel.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(sendOtpViewModel.getVerificationCallBack())
            .setForceResendingToken(sendOtpViewModel.refreshToken.value!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun observeOperation() {
        sendOtpViewModel.verificationState.observe(viewLifecycleOwner) {
            if(it) {
                completeUserInfo()
            }
        }
    }

    private fun completeUserInfo() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.USER_FORM,
                null
            )
        )
    }

    private fun sendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(sendOtpViewModel.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(sendOtpViewModel.getVerificationCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}