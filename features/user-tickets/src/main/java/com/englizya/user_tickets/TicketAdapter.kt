package com.englizya.user_tickets

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.response.UserTicket
import com.englizya.user_tickets.databinding.CardViewTicketBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class TicketAdapter(
    private val ticketList: List<UserTicket>,
    private val onItemClicked: (UserTicket) -> Unit,
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val bind = CardViewTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding = bind)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.updateUI(ticketList.get(position))

    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    inner class TicketViewHolder(
        private val binding: CardViewTicketBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun updateUI(ticket: UserTicket) {
            binding.source.text = ticket.source
//            binding.sourceTimeTV.text = TimeOnly.map(ticket.sourceTime)

            binding.destination.text = ticket.destination
//            binding.destinationTimeTV.text = TimeOnly.map(ticket.destinationTime)

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
            return BarcodeEncoder().encodeBitmap(
                ticket.ticketQr,
                BarcodeFormat.QR_CODE,
                400,
                400
            )
        }

        private fun updateBookingOfficeUI(ticket: UserTicket) {
            binding.station.text = ticket.bookingOfficeName
            binding.ridingTime.text = TimeOnly.map(ticket.bookingOfficeRidingTime)
            binding.exitTime.text = TimeOnly.map(ticket.bookingOfficeMovingTime)
        }

        private fun setupListener(ticket: UserTicket) {
            binding.root.setOnClickListener {
                Log.d("navigateToTicketDetails", ticket.ticketQr)

             it.findNavController().navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY,Destination.TICKET_DETAILS,false)
)
                //onItemClicked(ticket)
            }
        }

    }
}
