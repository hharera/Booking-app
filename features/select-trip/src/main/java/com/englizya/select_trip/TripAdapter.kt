package com.englizya.select_trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.CardViewTripBinding

class TripAdapter(
    private val trips: List<Trip>,
    private val onItemClicked: (Trip) -> Unit,
) : RecyclerView.Adapter<TripAdapter.NavigationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        val binding = CardViewTripBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(trips[position])
        holder.itemView.setOnClickListener {
            onItemClicked(trips.get(position))
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    class NavigationItemViewHolder(private val binding: CardViewTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(trip: Trip) {
            val sortedStations = trip.line.stations.sortedBy { it.stationOrder }

            binding.source.text = sortedStations.first().branch?.branchName
            binding.destination.text = sortedStations.last().branch?.branchName
        }
    }
}
