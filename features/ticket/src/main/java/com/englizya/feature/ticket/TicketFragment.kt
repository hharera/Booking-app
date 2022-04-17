package com.englizya.feature.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.feature.ticket.databinding.FragmentTicketMainBinding

class TicketFragment : BaseFragment() {

    private lateinit var binding: FragmentTicketMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketMainBinding.inflate(layoutInflater)
        return binding.root
    }
}