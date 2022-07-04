package com.englizya.user_tickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.response.CancelTicketResponse
import com.englizya.model.response.UserTicket
import com.englizya.user_tickets.databinding.FragmentUserTicketsBinding
import io.ktor.client.features.*
import io.ktor.http.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserTicketsFragment : BaseFragment() {

    private lateinit var binding: FragmentUserTicketsBinding
    private lateinit var adapter: TicketAdapter
    private val userTicketViewModel: UserTicketsViewModel by viewModel()
    var confirmationDialog: ConfirmationDialog? = null

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
        adapter = TicketAdapter(
            emptyList(),
            onCancelledClicked = { ticketId ->
                confirmationDialog = ConfirmationDialog(
                    onPositiveButtonClicked = {
                        cancelTicket(ticketId)
                    },
                    onNegativeButtonClicked = {
                        cancelDialog()
                    },
                    ticketId = ticketId
                )
                confirmationDialog?.show(childFragmentManager, "confirmationDialog")

            },
            onItemClicked = {
                showFullTicket(it)
            },
            onNextPageRequested = {
                userTicketViewModel.nextTicketsPage()
            }
        )
        binding.tickets.adapter = adapter
    }

    private fun cancelTicket(it: String?) {
        if (it != null) {
            userTicketViewModel.cancelTicket(it)
        }
    }

    private fun cancelDialog() {
        confirmationDialog?.dismiss()
        showToast("Deleting Ticket Cancelled")
    }

    private fun showFullTicket(it: UserTicket) {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.TICKET_DETAILS,
                it.ticketId.toString()
            )
        )
    }

    private fun setupObservers() {
        userTicketViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        userTicketViewModel.tickets.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        userTicketViewModel.cancelTicketStatus.observe(viewLifecycleOwner) {
            updateUI(it)
            adapter.clearList()
            userTicketViewModel.getFirstPageUserTickets()
        }

        userTicketViewModel.error.observe(viewLifecycleOwner) {
            confirmationDialog?.dismiss()
            handleFailure(it)
            showErrorDialog(it?.message!!.split("Text:")[1].dropWhile { it == '"' })
        }
    }

    private fun updateUI(userTickets: List<UserTicket>) {
        adapter.addTickets(userTickets)
    }

    private fun updateUI(cancellingStatus: CancelTicketResponse?) {
        if(cancellingStatus == null){
            confirmationDialog?.dismiss()
        }else{
            confirmationDialog?.dismiss()
            showToast(cancellingStatus!!.message)
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ticketSwipeLayout.setOnRefreshListener {
            userTicketViewModel.getUserTickets(true)
            binding.ticketSwipeLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        userTicketViewModel.getUserTickets(false)
    }

}