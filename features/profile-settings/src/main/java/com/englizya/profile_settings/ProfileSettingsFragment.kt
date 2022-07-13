package com.englizya.profile_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.User
import com.englizya.profile_settings.databinding.FragmentProfileSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileSettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileSettingsBinding
    private val profileSettingViewModel: ProfileSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileSettingsBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        profileSettingViewModel.user.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(user: User?) {
        if (user != null) {
            binding.name.setText(user.name)
            binding.address.setText(user.address)
        }

    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}