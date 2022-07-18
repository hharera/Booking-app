package com.englizya.internal_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.internal_search.databinding.StationListItemBinding
import com.englizya.model.model.InternalRoutes


class StationsAdapter(
    private var stations: List<InternalRoutes>,

    ) : RecyclerView.Adapter<StationsAdapter.NavigationItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
        val binding = StationListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    inner class NavigationItemViewHolder(private val binding: StationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(station: InternalRoutes) {

            binding.stationNameTxt.text = station.routeName

        }
    }

    fun setStations(list: List<InternalRoutes>) {
        stations = list
        Log.d("Stations from adapter", list.toString())
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(stations[position])
    }

    override fun getItemCount(): Int {
        return stations.size
    }
}