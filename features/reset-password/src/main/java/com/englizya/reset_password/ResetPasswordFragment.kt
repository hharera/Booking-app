package com.englizya.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.put_number.databinding.FragmentResetPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : BaseFragment() {

    private val resetPasswordViewModel: ResetPasswordViewModel by viewModel()
    private lateinit var bind: FragmentResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtras()
    }

    private fun getExtras() {
        arguments?.getString(Arguments.PHONE_NUMBER)?.let {
            resetPasswordViewModel.setPhoneNumber(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentResetPasswordBinding.inflate(layoutInflater)
        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        bind.password.afterTextChanged {
            //TODO
        }

        bind.confirmPassword.afterTextChanged {
            //TODO
        }
    }

    private fun setupObservers() {

    }

    override fun onResume() {
        super.onResume()
    }


}