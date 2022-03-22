package com.englizya.user_data

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.navigation.home.HomeActivity
import com.englizya.user_data.databinding.FragmentUserFormBinding

class UserFormFragment : BaseFragment() {

    private val userFormViewModel: UserFormViewModel by viewModels()
    private lateinit var bind: FragmentUserFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getString(Arguments.REDIRECT)?.let { redirect ->
                userFormViewModel.setRedirectRouting(redirect)
            }
        }

        changeStatusBarColor(R.color.blue_600)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentUserFormBinding.inflate(layoutInflater)

        return bind.root
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        bind.name.setText(userFormViewModel.name.value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        userFormViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        userFormViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(exception = it)
        }

        userFormViewModel.formValidity.observe(viewLifecycleOwner) {
            bind.save.isEnabled = it.isValid

            if (it.nameError != null) {
                bind.name.error = getString(it.nameError!!)
            }
        }
    }

    private fun setupListeners() {
        bind.name.afterTextChanged { name ->
            userFormViewModel.setName(name)
        }

        bind.save.setOnClickListener {
            goToHome()

            bind.save.isEnabled = false
        }
    }

    private fun goToHome() {
        activity?.startActivity(
            Intent(context, HomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        )
    }
}