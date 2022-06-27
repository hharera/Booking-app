package com.englizya.user_tickets

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
import com.englizya.user_tickets.databinding.FragmentUserTicketsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserTicketsFragment : BaseFragment() {

    private lateinit var binding: FragmentUserTicketsBinding
    private lateinit var adapter: TicketAdapter
    private val userTicketViewModel: UserTicketsViewModel by viewModel()
    var yesNoDialog: YesNoDialog? = null

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

                yesNoDialog = YesNoDialog(
                    onPositiveButtonClicked = {
                        cancelTicket(ticketId)
                    },
                    onNegativeButtonClicked = {
                        cancelDialog()
                    },
                    ticketId = ticketId
                )

            }, onItemClicked = {
                showFullTicket(it)
            })
        binding.tickets.adapter = adapter
    }

    private fun setupAdapter(tickets: List<UserTicket>) {
        adapter = TicketAdapter(tickets, onItemClicked = {
            showFullTicket(it)
        }, onCancelledClicked = { ticketId ->

            yesNoDialog = YesNoDialog(
                onPositiveButtonClicked = {
                    cancelTicket(ticketId)
                },
                onNegativeButtonClicked = {
                    cancelDialog()
                },
                ticketId = ticketId
            )
            yesNoDialog?.show(childFragmentManager, "yesNoDialog")


        })
        binding.tickets.adapter = adapter
    }
    private fun cancelTicket(it: String?) {

        if (it != null) {
            userTicketViewModel.cancelTicket(it)
        }

    }

//

    private fun cancelDialog() {
        yesNoDialog?.dismiss()
        showToast("Deleting Ticket Cancelled")
        //  findNavController ().popBackStack()
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
            setupAdapter(it)
        }

        userTicketViewModel.cancelTicketStatus.observe(viewLifecycleOwner) {
            updateUI(it)
        }
        userTicketViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun updateUI(cancellingStatus: CancelTicketResponse?) {
        if (cancellingStatus?.status == "success") {
            yesNoDialog?.dismiss()
            onResume()
            showToast(cancellingStatus.message)


        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        userTicketViewModel.getUserTickets()
    }

}