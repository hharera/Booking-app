package com.englizya.terms_conditions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.terms_conditions.databinding.FragmentTermsAndConditionsBinding

class TermsAndConditionsFragment : BaseFragment() {

    private lateinit var binding: FragmentTermsAndConditionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTermsAndConditionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}