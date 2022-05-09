package com.englizya.select_station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStation
import com.englizya.model.model.LineStationTime
import com.englizya.select_station.databinding.CardViewStopStationBinding

class StationAdapter(
    private val stations: ArrayList<LineStationTime>,
    private val onItemClicked: (LineStationTime) -> Unit,
) : RecyclerView.Adapter<StationAdapter.NavigationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        val binding = CardViewStopStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(stations[position])
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    inner class NavigationItemViewHolder(private val binding: CardViewStopStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(stationTime: LineStationTime) {
            binding.station.text = stationTime.bookingOffice?.officeName

            binding.source.text = TimeOnly.map(stationTime.startTime)
            binding.destination.text = TimeOnly.map(stationTime.startTime)

            binding.root.setOnClickListener {
                onItemClicked(stationTime)
            }
        }
    }
}
