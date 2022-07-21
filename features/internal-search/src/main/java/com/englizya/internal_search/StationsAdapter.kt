package com.englizya.internal_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.internal_search.databinding.StationDialogListItemBinding
import com.englizya.internal_search.databinding.StationListItemBinding
import com.englizya.model.model.RouteStations


class StationsAdapter(
    private var stations: List<RouteStations>,
    private val onItemClicked: (RouteStations) -> Unit,


    ) : RecyclerView.Adapter<StationsAdapter.StationItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationItemViewHolder {
        val binding = StationDialogListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: StationItemViewHolder, position: Int) {
        holder.updateUI(stations[position])
        holder.itemView.setOnClickListener { onItemClicked(stations[position]) }
    }

    override fun getItemCount(): Int {
        Log.d("Sizes", stations.size.toString())
        return stations.size
    }

    inner class StationItemViewHolder(private val binding: StationDialogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun updateUI(routeStation: RouteStations) {
            binding.stationNameTxt.text = routeStation.stationName

        }

    }

    fun setStations(list: List<List<RouteStations>>) {

        stations = list.flatten()
        Log.d("Stations from adapter", list.toString())
        Log.d("Size Stations from adapter", list.size.toString())
        notifyDataSetChanged()

    }

    fun filter(text: String) {
        val filteredList: ArrayList<RouteStations> = ArrayList()
        filteredList.clear()
        for (item in stations) {
            if (item.stationName.contains(text)) {
                filteredList.add(item)
            }

        }
        Log.d("Filtered", filteredList.toString())
        filterList(filteredList);

    }

    private fun filterList(filteredList: ArrayList<RouteStations>) {
        stations = filteredList
        notifyDataSetChanged()
    }
}