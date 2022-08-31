package com.englizya.user_tickets

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
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


class TicketAdapter(
    private var ticketList: List<UserTicket>,
    private val onItemClicked: (UserTicket) -> Unit,
    private val onCancelledClicked: (String) -> Unit,
) : RecyclerView.Adapter<TicketAdapter.BaseViewHolder>() {

    companion object {
        private const val TAG = "TicketAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        CardViewTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            return TicketViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder as TicketViewHolder).updateUI(ticketList[position])
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    fun clearList() {
        ticketList.forEach {
            val index = ticketList.lastIndex
            ticketList.dropLast(1)
            notifyItemRemoved(index)
        }
    }

    fun setTickets(userTickets: List<UserTicket>) {
        Log.d(TAG, "setTickets: ${userTickets.size}")
        ticketList = userTickets
        notifyDataSetChanged()
    }

    private fun addList(userTickets: List<UserTicket>) {
        userTickets.forEach { userTicket ->
            ticketList = ticketList.plus(userTicket)
            notifyItemInserted(ticketList.lastIndex)
        }
    }

    open inner class BaseViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

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
