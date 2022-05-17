package com.englizya.select_trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.select_trip.databinding.CardViewBookingOfficeBinding

class BookingOfficeAdapter(
    private var stations: List<LineStationTime>,
    private var selectedBookingOfficeId: Int = 0,
    private val onItemClicked: (LineStationTime) -> Unit,
) : RecyclerView.Adapter<BookingOfficeAdapter.BookingOfficeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingOfficeViewHolder {
        val bind = CardViewBookingOfficeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingOfficeViewHolder(binding = bind)
    }

    override fun onBindViewHolder(holder: BookingOfficeViewHolder, position: Int) {
        holder.updateUI(stations.get(position))

//        if (selectedBookingOfficeId == stations.get(position).bookingOffice?.officeId) {
//            holder.updateUI(isClicked = true)
//        } else {
//            holder.updateUI(isClicked = false)
//        }
    }

    override fun getItemCount(): Int {
        return stations.size
    }


    inner class BookingOfficeViewHolder(
        private val binding: CardViewBookingOfficeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun updateUI(stopStation: LineStationTime) {
            stopStation.bookingOffice?.officeName
                .plus("\n")
                .plus(TimeOnly.map(stopStation.startTime))
                .let {
                    binding.textViewStopStationName.text = it
                }

            setupListener(stopStation)
        }

        private fun setupListener(stopStation: LineStationTime) {
            binding.root.setOnClickListener {
                onItemClicked(stopStation)

                stopStation.bookingOffice?.officeId?.let {
                    selectedBookingOfficeId = it
                }
            }
        }

//        fun updateUI(isClicked: Boolean) {
//            if (isClicked) {
//
//                binding.card
//                    .setCardBackgroundColor(
//                        ContextCompat.getColor(
//                            binding.root.context,
//                            R.color.colorPrimary
//                        )
//                    )
//
//                binding
//                    .textViewStopStationName
//                    .setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_100))
//            } else {
//
//                binding
//                    .card
//                    .setCardBackgroundColor(
//                        ContextCompat.getColor(
//                            binding.root.context,
//                            R.color.grey_100
//                        )
//                    )
//
//                binding
//                    .textViewStopStationName
//                    .setTextColor(
//                        ContextCompat.getColor(
//                            binding.root.context,
//                            R.color.colorPrimary
//                        )
//                    )
//            }
//        }
    }

    companion object {
        private const val TAG = "StopStationAdapter"
    }
}
