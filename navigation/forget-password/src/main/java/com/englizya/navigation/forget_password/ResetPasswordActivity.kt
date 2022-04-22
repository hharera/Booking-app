package com.englizya.navigation.forget_password

import android.os.Bundle
import androidx.navigation.NavController
import com.englizya.common.base.BaseActivity
import com.englizya.navigation.forget_password.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : BaseActivity() {

    private lateinit var bind: ActivityResetPasswordBinding
    private lateinit var navController: NavController
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}