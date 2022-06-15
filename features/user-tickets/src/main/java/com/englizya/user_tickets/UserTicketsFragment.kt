package com.englizya.user_tickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.model.response.CancelTicketResponse
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
            onCancelledClicked = { ticketId ->
                confirmationDialog(ticketId)
            }, onItemClicked = {
                showFullTicket(it)
            })
        binding.tickets.adapter = adapter
    }

    private fun setupAdapter(tickets: List<UserTicket>) {
        adapter = TicketAdapter(tickets, onItemClicked = {
            showFullTicket(it)
        }, onCancelledClicked = { ticketId ->

            confirmationDialog(ticketId).show(childFragmentManager, "Cancelling Ticket")

        })
        binding.tickets.adapter = adapter
    }

    private fun cancelTicket(it: String?) {

        if (it != null) {
            userTicketViewModel.cancelTicket(it)
        }

   }

    private fun confirmationDialog(ticketId: String?): YesNoDialog {
        if (ticketId != null) {
            return YesNoDialog(

                onPositiveButtonClicked = {
                    cancelTicket(ticketId)
                },
                onNegativeButtonClicked = {
                    cancelDialog()
                },
                ticketId = ticketId
            )
        } else{
            return YesNoDialog(
                onPositiveButtonClicked = {
                    cancelDialog()
                },
                onNegativeButtonClicked = {
                    cancelDialog()
                }
            ,ticketId = null
            )
        }

    }

    private fun cancelDialog() {
        showToast("Deleting Ticket Cancelled")
        //  findNavController ().popBackStack()
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

    private fun updateUI(cancellingStatus: CancelTicketResponse?) {
        if (cancellingStatus?.status == "Ticket Cancelled") {
            Log.d("Cancelling from Fragment", cancellingStatus.message)

            confirmationDialog(null).dismiss()

            onResume()
        } else {
            Log.d("Cancelling from Fragment", cancellingStatus!!.message)

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