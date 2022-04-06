package com.englizya.select_trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.model.model.Station
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.CardViewTripBinding

class TripAdapter(
    private val trips: List<Trip>,
    val source: Station?,
    private val destination: Station?,
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
        holder.updateUI(trips[position], source, destination)
        holder.itemView.setOnClickListener {
            onItemClicked(trips.get(position))
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    class NavigationItemViewHolder(private val binding: CardViewTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(trip: Trip, source: Station?, destination: Station?) {
            val sortedStations = trip.line.stations.sortedBy { it.stationOrder }

            binding.source.text = source?.branchName
            binding.sourceTimeTV.text = sortedStations.first().startDate

            binding.destination.text = destination?.branchName
            binding.destinationTimeTV.text = sortedStations.last().startDate

            binding.price.text = trip.plan?.seatPrices?.firstOrNull {
                it.source == source?.branchId && it.destination == destination?.branchId
            }?.vipPrice.toString()
        }
    }
}
