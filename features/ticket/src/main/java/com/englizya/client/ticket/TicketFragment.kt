package com.englizya.client.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.client.ticket.databinding.FragmentTicketMainBinding
import com.englizya.common.base.BaseFragment

class TicketFragment : BaseFragment() {

    private lateinit var binding : FragmentTicketMainBinding


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