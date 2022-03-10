package com.harera.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.englizya.common.base.BaseViewModel
import com.englizya.signup.LoginViewModel
import com.harera.common.utils.navigation.Arguments
import com.englizya.confirm_login.ConfirmLoginActivity
import com.harera.login.databinding.ActivityLoginBinding

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

        bind.gridLayout.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> loginViewModel.changePhoneNumber("0")
                    R.id.one -> loginViewModel.changePhoneNumber("1")
                    R.id.two -> loginViewModel.changePhoneNumber("2")
                    R.id.three -> loginViewModel.changePhoneNumber("3")
                    R.id.four -> loginViewModel.changePhoneNumber("4")
                    R.id.five -> loginViewModel.changePhoneNumber("5")
                    R.id.six -> loginViewModel.changePhoneNumber("6")
                    R.id.seven -> loginViewModel.changePhoneNumber("7")
                    R.id.eight -> loginViewModel.changePhoneNumber("8")
                    R.id.nine -> loginViewModel.changePhoneNumber("9")
                    R.id.remove -> loginViewModel.removeChar()
                }
            }
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