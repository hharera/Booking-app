package com.englizya.app_settings

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.app_settings.databinding.FragmentAppSettingsBinding
import com.englizya.common.base.BaseFragment
import com.englizya.datastore.utils.Language
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
            settingsViewModel.saveSelectedLanguage()
            binding.save.isEnabled = false
            updateLanguage()
            activity?.onBackPressed()
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateLanguage() {
        val locale = Locale.Builder().setLanguage(settingsViewModel.selectedLanguage.value!!.key).build()
        Locale.setDefault(locale)
        val resources: Resources = activity!!.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        context?.createConfigurationContext(config)
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