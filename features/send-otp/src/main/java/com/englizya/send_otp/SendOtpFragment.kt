package com.englizya.send_otp

import android.os.Bundle
import android.util.Log
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

class SendOtpFragment : BaseFragment() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private lateinit var bind: FragmentSendOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPhoneNumberArgument()
    }

    private fun getPhoneNumberArgument() {
        arguments?.let {
            it.getString(PHONE_NUMBER)?.let { phoneNumber ->
                Log.d(TAG, "getPhoneNumberArgument: $phoneNumber")
                sendOtpViewModel.setPhoneNumber(appendCode(phoneNumber, CountryCode.EgyptianCode))
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
        bind.numbersGrid.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> sendOtpViewModel.putCharacter("0")
                    R.id.one -> sendOtpViewModel.putCharacter("1")
                    R.id.two -> sendOtpViewModel.putCharacter("2")
                    R.id.three -> sendOtpViewModel.putCharacter("3")
                    R.id.four -> sendOtpViewModel.putCharacter("4")
                    R.id.five -> sendOtpViewModel.putCharacter("5")
                    R.id.six -> sendOtpViewModel.putCharacter("6")
                    R.id.seven -> sendOtpViewModel.putCharacter("7")
                    R.id.eight -> sendOtpViewModel.putCharacter("8")
                    R.id.nine -> sendOtpViewModel.putCharacter("9")
                    R.id.delete -> sendOtpViewModel.removeCharacter()
                }
            }
        }
    }

    private fun setupListeners() {
        bind.next.setOnClickListener {
            lifecycleScope.launch {
                sendOtpViewModel.signup()
            }
        }
    }

    private fun setupObservers() {
        observeOperation()

        sendOtpViewModel.phoneNumber.observe(viewLifecycleOwner) {
            updateUI(it)
            sendVerificationCode()
        }

        sendOtpViewModel.code.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                sendOtpViewModel.checkCodeValidity()
            }
        }

        sendOtpViewModel.codeValidity.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                sendOtpViewModel.signup()
            }
        }

        sendOtpViewModel.verificationCode.observe(viewLifecycleOwner) { verificationCode ->
            promptCode()
        }
    }

    private fun updateUI(phoneNumber: String) {
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
            if(it)
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
            .setPhoneNumber(sendOtpViewModel.phoneNumber.value!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(sendOtpViewModel.getVerificationCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}