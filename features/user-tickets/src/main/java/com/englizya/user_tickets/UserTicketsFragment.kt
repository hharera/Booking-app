package com.englizya.user_tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.model.response.UserTicket
import com.englizya.user_tickets.databinding.FragmentUserTicketsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserTicketsFragment : BaseFragment() {

    private lateinit var binding: FragmentUserTicketsBinding
    private lateinit var adapter: TicketAdapter
    private val userTicketViewModel: UserTicketsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserTicketsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        setupListeners()
        setupObservers()
    }

    private fun setupAdapter() {
        adapter = TicketAdapter(emptyList()) {
            showFullTicket(it)
        }
        binding.tickets.adapter = adapter
    }

    private fun setupAdapter(tickets: List<UserTicket>) {
        adapter = TicketAdapter(tickets) {
            showFullTicket(it)
        }
        binding.tickets.adapter = adapter
    }

    private fun showFullTicket(it: UserTicket) {

    }

    private fun setupObservers() {
        userTicketViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        userTicketViewModel.tickets.observe(viewLifecycleOwner) {
            setupAdapter(it)
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        userTicketViewModel.getUserTickets()
    }

}