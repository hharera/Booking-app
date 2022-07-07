package com.englizya.app_settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.app_settings.databinding.FragmentAppSettingsBinding
import com.englizya.common.base.BaseFragment
import com.englizya.datastore.utils.Language
import com.jakewharton.processphoenix.ProcessPhoenix
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentAppSettingsBinding
    private val settingsViewModel: SettingsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAppSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        settingsViewModel.appLanguage.value?.let {
            updateUI(it)
        }

        settingsViewModel.selectedLanguage.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(language: String) {
        when (language) {
            Language.Arabic.key -> updateUI(Language.Arabic)
            Language.English.key -> updateUI(Language.English)
        }
    }

    private fun setupListeners() {
        binding.arabic.setOnClickListener {
            settingsViewModel.setSelectedLanguage(Language.Arabic)
        }

        binding.english.setOnClickListener {
            settingsViewModel.setSelectedLanguage(Language.English)
        }

        binding.save.setOnClickListener {
            settingsViewModel.saveSelectedLanguage().also {
                restartApp()
            }
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun restartApp() {
        val mStartActivity =
            Intent(context, Class.forName("com.englizya.splash.SplashActivity"))
        ProcessPhoenix.triggerRebirth(context, mStartActivity)
    }


    private fun updateUI(language: Language) {
        when (language) {
            is Language.Arabic -> {
                binding.arabicRadioButton.isChecked = true
                binding.englishRadioButton.isChecked = false
            }

            is Language.English -> {
                binding.arabicRadioButton.isChecked = false
                binding.englishRadioButton.isChecked = true
            }
        }
    }
}