package com.englizya.internal_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.internal_search.databinding.StationListItemBinding
import com.englizya.model.model.RouteStations

class SearchResultStationsAdapter(
    private var stations: List<RouteStations>,

    ) : RecyclerView.Adapter<SearchResultStationsAdapter.StationsViewHolder>() {
    inner class StationsViewHolder(private val binding: StationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun updateUI(station: RouteStations) {
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultStationsAdapter.StationsViewHolder {
        val binding = StationListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StationsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
        holder.updateUI(stations[position])
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    fun setStations(list: List<RouteStations>) {

        stations = list
        notifyDataSetChanged()

    }
}