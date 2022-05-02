package com.englizya.user_tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.model.model.TripTimes
import com.englizya.user_tickets.databinding.CardViewStopStationBinding

class StopStationAdapter(
    private val stations: List<TripTimes>,
    private val onItemClicked: (TripTimes) -> Unit,
) : RecyclerView.Adapter<StopStationAdapter.StopViewHolder>() {

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

    class StopViewHolder(
        private val binding: CardViewStopStationBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(stopStation: TripTimes) {
            stopStation.bookingOffice?.officeName
                .plus("\n")
                .plus(TimeOnly.map(stopStation.startTime))
                .let {
                    binding.textViewStopStationName.text = it
                }
        }
    }
}
