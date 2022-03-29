package com.englizya.app_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.app_settings.databinding.FragmentAppSettingsBinding
import com.englizya.common.base.BaseFragment

class AppSettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentAppSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

}