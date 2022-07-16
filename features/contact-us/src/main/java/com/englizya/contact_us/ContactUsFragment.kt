package com.englizya.contact_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.contact_us.ContactInfo.FACEBOOK
import com.englizya.contact_us.ContactInfo.LINKEDIN
import com.englizya.contact_us.ContactInfo.WEB
import com.englizya.contact_us.databinding.FragmentContactUsBinding

class ContactUsFragment : BaseFragment() {

    private lateinit var binding: FragmentContactUsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContactUsBinding.inflate(layoutInflater)
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