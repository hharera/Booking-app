package com.englizya.privacy_policy;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.privacy_policy.databinding.FragmentPrivacyPolicyBinding
import java.util.*

class PrivacyPolicyFragment : BaseFragment() {

    private lateinit var binding: FragmentPrivacyPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPrivacyPolicyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        checkLanguage()
    }

    private fun checkLanguage() {
        when (Locale.getDefault().language) {
            "ar" -> {
                binding.textPrivacyPolicy.text = PrivacyPolicy.ARABIC_DETAILS
            }
            "en" -> {
                binding.textPrivacyPolicy.text = PrivacyPolicy.ENGLISH_DETAILS
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}