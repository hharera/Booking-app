package com.englizya.user_tickets

import android.content.Intent
import android.os.Bundle
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
import com.englizya.repository.utils.Resource
import com.englizya.user_tickets.databinding.FragmentUserTicketsBinding
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

        userTicketViewModel.page.observe(viewLifecycleOwner) {
            binding.pageNumber.text = it.plus(1).toString()
        }

        userTicketViewModel.tickets.observe(viewLifecycleOwner) {
            handleTicketsResult(it)
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

    private fun handleTicketsResult(result: Resource<List<UserTicket>>?) {
        if (result != null) {
            checkResultStatus(result)
        }
    }

    private fun checkResultStatus(result: Resource<List<UserTicket>>) {
        when (result) {
            is Resource.Success -> {
                handleLoading(false)
                updateUI(result.data!!)
            }

            is Resource.Error -> {
                handleLoading(false)
                handleFailure(result.error)
            }

            is Resource.Loading -> {
                handleLoading(true)
            }
        }
    }

    private fun updateUI(userTickets: List<UserTicket>) {
        if (userTickets.isEmpty()) {
            binding.emptyView.root.visibility = View.VISIBLE
            binding.ticketSwipeLayout.visibility = View.INVISIBLE
        } else {
            binding.emptyView.root.visibility = View.INVISIBLE
            binding.ticketSwipeLayout.visibility = View.VISIBLE
            setupAdapter().also {
                adapter.setTickets(userTickets)
            }
        }
    }

    private fun updateUI(cancellingStatus: CancelTicketResponse?) {
        if (cancellingStatus == null) {
            confirmationDialog?.dismiss()
        } else {
            confirmationDialog?.dismiss()
            showToast(cancellingStatus.message)
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

        binding.emptyView.goBook.setOnClickListener {
            navigateToBooking()
        }

        binding.previousPage.setOnClickListener {
            getPreviousTicketsPage()
        }

        binding.nextPage.setOnClickListener {
            getNextTicketsPage()
        }
    }

    private fun getNextTicketsPage() {
        userTicketViewModel.nextTicketsPage().observe(viewLifecycleOwner) {
            handleTicketsResult(it)
        }
    }

    private fun getPreviousTicketsPage() {
        userTicketViewModel.previousTicketsPage().observe(viewLifecycleOwner) {
            handleTicketsResult(it)
        }
    }

    private fun navigateToBooking() {
        startActivity(
            Intent(
                context,
                Class.forName("com.englizya.navigation.booking.BookingActivity")
            )
        )
    }
}