package com.englizya.send_otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.Response
import com.englizya.common.utils.Status
import com.englizya.common.utils.navigation.Arguments.PHONE_NUMBER
import com.englizya.send_otp.databinding.FragmentSendOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ConfirmLoginActivity : BaseFragment() {

    private val confirmLoginViewMode: ConfirmLoginViewModel by viewModels()
    private lateinit var bind: FragmentSendOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPhoneNumber()
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

    private fun setupListeners() {
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


        bind.next.setOnClickListener {
            confirmLoginViewMode.login()
        }
    }

    private fun setupObservers() {
        observeOperation()

        confirmLoginViewMode.phoneNumber.observe(viewLifecycleOwner) {
            sendVerificationCode("+2$it")
        }

        confirmLoginViewMode.code.observe(viewLifecycleOwner) {
            confirmLoginViewMode.checkCodeValidity()
        }

        confirmLoginViewMode.codeValidity.observe(viewLifecycleOwner) {
            bind.next.isEnabled = it
        }

        confirmLoginViewMode.verificationCode.observe(viewLifecycleOwner) { response ->
            updateUI(response)
        }
    }

    private fun updateUI(response: Response<String>) {
        when(response.status) {
            Status.ERROR -> {

            }

            Status.LOADING -> {

            }

            Status.SUCCESS -> {

            }
        }
    }

    private fun getPhoneNumber() {
        arguments?.let {
            it.getString(PHONE_NUMBER)?.let { phoneNumber ->
                confirmLoginViewMode.setPhoneNumber(phoneNumber)
            }
        }
    }

    private fun observeOperation() {
        confirmLoginViewMode.loginOperation.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    handleSuccess()
                    goToHomeActivity()
                }

                Status.ERROR -> {
                    handleFailure(it.error!!)
                }

                Status.LOADING -> {
                    handleLoading()
                }
            }
        }
    }

    private fun goToHomeActivity() {
        finish()
        overridePendingTransition(R.anim.slide_in_right, 0)
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(confirmLoginViewMode.createCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}