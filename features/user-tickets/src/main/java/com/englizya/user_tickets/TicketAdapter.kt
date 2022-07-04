package com.englizya.user_tickets

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.englizya.common.ui.ColoredQr
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.response.UserTicket
import com.englizya.user_tickets.databinding.CardViewLoadingBinding
import com.englizya.user_tickets.databinding.CardViewTicketBinding
import com.englizya.user_tickets.utils.CardType


class TicketAdapter(
    private var ticketList: List<UserTicket>,
    private val onNextPageRequested: () -> Unit,
    private val onItemClicked: (UserTicket) -> Unit,
    private val onCancelledClicked: (String) -> Unit,
) : RecyclerView.Adapter<TicketAdapter.BaseViewHolder>() {

    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return if (position == ticketList.size) {
            CardType.LOADING
        } else {
            CardType.TICKET
        }
    }

    fun addTickets(tickets: List<UserTicket>) {
        tickets.forEach {
            addTicket(it)
        }
    }

    private fun addTicket(ticket: UserTicket) {
        ticketList = ticketList.plus(ticket)
        notifyItemInserted(ticketList.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            CardType.LOADING -> {
                LoadingViewHolder(
                    CardViewLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                CardViewTicketBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).let {
                    TicketViewHolder(it)
                }
            }
        }
    }

    private fun requestNextPage() {
        if (isLoading.not()) {
            isLoading = true
            onNextPageRequested()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == CardType.TICKET) {
            (holder as TicketViewHolder).updateUI(ticketList[position])
        } else {
            requestNextPage()
        }
    }

    override fun getItemCount(): Int {
        return if (ticketList.isEmpty()) {
            0
        } else {
            ticketList.size + 1
        }
    }

    fun clearList() {
        ticketList.forEachIndexed { index, userTicket ->
            notifyItemRemoved(index)
        }
        ticketList = emptyList()
        notifyDataSetChanged()
    }

    open inner class BaseViewHolder(binding : ViewBinding) : RecyclerView.ViewHolder(binding.root)

    inner class LoadingViewHolder(
        private val binding: CardViewLoadingBinding
    ) : BaseViewHolder(binding) {}

    inner class TicketViewHolder(
        private val binding: CardViewTicketBinding
    ) : BaseViewHolder(binding) {

        fun updateUI(ticket: UserTicket) {
            binding.source.text = ticket.source
            binding.sourceTimeTV.text = TimeOnly.map(ticket.sourceTime)

            binding.destination.text = ticket.destination
            binding.destinationTimeTV.text = TimeOnly.map(ticket.destinationTime)

            binding.tripDate.text = DateOnly.toMonthDate(ticket.reservationDate)
            binding.trioId.text = (ticket.tripId).toString().let { "#Trip $it" }
            binding.seatNo.text = (ticket.seatNo).toString().let { "#Seat $it" }
            binding.ticketId.text = (ticket.ticketId).toString().let { "#Ticket $it" }
            binding.serviceDegree.text = ticket.serviceType
            createTicketQr(ticket).let {
                binding.ticketQr.setImageBitmap(it)
            }

            setupListener(ticket)
            updateBookingOfficeUI(ticket)
        }

        private fun createTicketQr(ticket: UserTicket): Bitmap {
            return ColoredQr().generateQRCode(ticket.ticketQr, ticket.isActive)
        }

        private fun updateBookingOfficeUI(ticket: UserTicket) {
            binding.station.text = ticket.bookingOfficeName
            binding.ridingTime.text = TimeOnly.map(ticket.bookingOfficeRidingTime)
            binding.exitTime.text = TimeOnly.map(ticket.bookingOfficeMovingTime)
        }

        private fun setupListener(ticket: UserTicket) {
            binding.root.setOnClickListener {
                Log.d("navigateToTicketDetails", ticket.ticketId.toString())
                onItemClicked(ticket)

                it.findNavController().navigate(
                    NavigationUtils.getUriNavigation(
                        Domain.ENGLIZYA_PAY,
                        Destination.TICKET_DETAILS,
                        ticket.ticketId.toString()
                    )

                )
                //onItemClicked(ticket)
            }
            binding.cancelBtn.setOnClickListener {
                Log.d("Cancelling Ticket from Adapter" , "Cancelling")
                onCancelledClicked(ticket.ticketId.toString())
            }
        }
    }
}
