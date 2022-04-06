package com.englizya.select_station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.model.model.LineStation
import com.englizya.select_station.databinding.CardViewStopStationBinding

class StationAdapter(
    private val stations: List<LineStation>,
    private val onItemClicked: (LineStation) -> Unit,
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
        holder.updateUI(stations[position], stations[position + 1])

        holder.itemView.setOnClickListener {
            onItemClicked(stations[position + 1])
        }
    }

    override fun getItemCount(): Int {
        return stations.size - 1
    }

    class NavigationItemViewHolder(private val binding: CardViewStopStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(source: LineStation, destination: LineStation) {
            binding.station.text = destination.branch?.branchName

            binding.source.text = source.branch?.branchName
            binding.destination.text = destination.branch?.branchName
        }
    }
}
