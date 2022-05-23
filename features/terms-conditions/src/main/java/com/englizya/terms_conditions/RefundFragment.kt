package com.englizya.terms_conditions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.terms_conditions.databinding.FragmentRefundBinding
import com.englizya.terms_conditions.utils.RefundPolicy
import java.util.*

class RefundFragment : BaseFragment() {

    private lateinit var binding: FragmentRefundBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRefundBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRefundPolicy()
    }

    private fun setupRefundPolicy() {
        when (Locale.getDefault().language) {
            "ar" -> {
                binding.refundPolicyDetails.text = RefundPolicy.ARABIC
            }
            "en" -> {
                binding.refundPolicyDetails.text = RefundPolicy.ENGLISH
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}