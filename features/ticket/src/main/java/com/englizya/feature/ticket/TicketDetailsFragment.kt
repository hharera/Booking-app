package com.englizya.feature.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.feature.ticket.databinding.FragmentTicketDetailsBinding

class TicketDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentTicketDetailsBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTicketDetailsBinding.inflate(layoutInflater)
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