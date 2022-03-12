package com.harera.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.englizya.common.base.BaseViewModel
import com.englizya.login.LoginViewModel
import com.englizya.confirm_login.ConfirmLoginActivity

class LoginActivity : BaseViewModel() {

    lateinit var loginViewModel: LoginViewModel
    lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.phoneNumber.observe(this) {
            bind.phoneNumber.text = it
            loginViewModel.checkPhoneNumberValidity()
        }

        loginViewModel.phoneNumberValidity.observe(this) {
            bind.next.isEnabled = it
        }

        loginViewModel.policyAccepted.observe(this) {
            bind.next.isEnabled = bind.next.isEnabled && it
            if (it) {
                bind.acceptPolicy.setImageResource(R.drawable.verified)
            } else
                bind.acceptPolicy.setImageResource(R.drawable.not_verified)
        }

        bind.next.setOnClickListener {
            confirmLogin(loginViewModel.phoneNumber.value!!)
        }

        bind.acceptPolicy.setOnClickListener {
            loginViewModel.acceptPolicy()
        }
    }

    private fun confirmLogin(phoneNumber : String) {
        val intent = Intent(this, ConfirmLoginActivity::class.java).apply {
            putExtra(Arguments.PHONE_NUMBER, phoneNumber)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }
}