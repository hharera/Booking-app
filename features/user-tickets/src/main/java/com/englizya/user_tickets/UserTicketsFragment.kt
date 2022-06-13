package com.englizya.user_tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserTicketsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListeners()
        setupObservers()
    }

    private fun setupAdapter() {
        adapter = TicketAdapter(emptyList(),
            onCancelledClicked = {
                cancelTicket(it)
            }, onItemClicked = {
                showFullTicket(it)
            })
        binding.tickets.adapter = adapter
    }

    private fun setupAdapter(tickets: List<UserTicket>) {
        adapter = TicketAdapter(tickets, onItemClicked = {
            showFullTicket(it)
        }, onCancelledClicked = {
            YesNoDialog(onPositiveButtonClicked = {
                cancelTicket(it)
            }, onNegativeButtonClicked = {
                cancelDialog(it)
            })

        })
        binding.tickets.adapter = adapter
    }

    private fun cancelTicket(it: String) {
        userTicketViewModel.cancelTicket(it)
    }

    private fun cancelDialog(it: String) {
        Toast.makeText(activity , "Deleting Ticket Cancelled",Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
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

        userTicketViewModel.cancelTicketStatus.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(cancellingStatus: Boolean?) {
        if (cancellingStatus == true) {
            showToast("Ticket deleted successfully")
            onResume()
        } else {
            showToast("Ticket can't be cancelled")
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