package com.englizya.about_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.about_us.databinding.FragmentAboutUsBinding
import com.englizya.common.base.BaseFragment

class PrivacyPolicyFragment : BaseFragment() {

    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAboutUsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.web.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(WEB)
                startActivity(this)
            }
        }

        binding.facebook.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(FACEBOOK)
                startActivity(this)
            }
        }

        binding.linkedin.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(LINKEDIN)
                startActivity(this)
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}