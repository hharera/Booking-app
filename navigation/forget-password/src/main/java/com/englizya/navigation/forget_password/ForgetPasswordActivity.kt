package com.englizya.navigation.forget_password

import android.os.Bundle
import androidx.navigation.NavController
import com.englizya.common.base.BaseActivity
import com.englizya.navigation.forget_password.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : BaseActivity() {

    private lateinit var bind: ActivityForgetPasswordBinding
    private lateinit var navController: NavController
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}