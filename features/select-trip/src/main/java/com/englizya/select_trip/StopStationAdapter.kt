package com.englizya.select_trip

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.select_trip.databinding.CardViewStopStationBinding

class StopStationAdapter(
    private val stations: List<LineStationTime>,
    private val onItemClicked: (LineStationTime) -> Unit,
) : RecyclerView.Adapter<StopStationAdapter.StopViewHolder>() {

    private var selectedBookingOfficeId = -100

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val bind = CardViewStopStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StopViewHolder(binding = bind)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        holder.updateUI(stations.get(position))
        holder.itemView.setOnClickListener {
            onItemClicked(stations.get(position))
        }
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    inner class StopViewHolder(
        private val binding: CardViewStopStationBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(stopStation: LineStationTime) {
            stopStation.bookingOffice?.officeName
                .plus("\n")
                .plus(TimeOnly.map(stopStation.startTime))
                .let {
                    binding.textViewStopStationName.text = it
                }


            if (selectedBookingOfficeId == stopStation.bookingOffice?.officeId) {
                Log.d(Companion.TAG, "stopStation: $selectedBookingOfficeId")
                binding
                    .root
                    .setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))

                binding
                    .textViewStopStationName
                    .setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {

                binding
                    .root
                    .setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))

                binding
                    .textViewStopStationName
                    .setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
            }

            setupListener(stopStation)
        }

        private fun setupListener(stopStation: LineStationTime) {
            binding.root.setOnClickListener {
                Log.d(TAG, "setupListener: ")
                stopStation.bookingOffice?.officeId?.let {
                    selectedBookingOfficeId = it
                }
                onItemClicked(stopStation)

                binding
                    .root
                    .setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))

                binding
                    .textViewStopStationName
                    .setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            }
        }
    }

    companion object {
        private const val TAG = "StopStationAdapter"
    }
}
