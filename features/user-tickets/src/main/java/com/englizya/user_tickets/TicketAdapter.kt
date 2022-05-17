package com.englizya.user_tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.TripTimes
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
            binding.sourceTimeTV.text = TimeOnly.map(ticket.sourceTime)

            binding.destination.text = ticket.destination
            binding.destinationTimeTV.text = TimeOnly.map(ticket.destinationTime)

//            binding.tripDate.text = DateOnly.toMonthDate(ticket.tripDate)
            binding.seatNo.text = (ticket.seatNo).toString()
            binding.serviceDegree.text = (ticket.serviceType)

            setupStationsAdapter(ticket.sourceOfficeList)

            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.encodeBitmap(
                ticket.ticketQr,
                BarcodeFormat.QR_CODE,
                400,
                400
            ).let {
                binding.ticketQr.setImageBitmap(it)
            }


            setupListener(ticket)
        }

        private fun setupStationsAdapter(sourceOfficeList: List<TripTimes>) {
            val adapter = StopStationAdapter(sourceOfficeList){

            }
            binding.stations.adapter = adapter
        }

        private fun setupListener(ticket: UserTicket) {
            binding.root.setOnClickListener {
                onItemClicked(ticket)
            }
        }

    }
}
