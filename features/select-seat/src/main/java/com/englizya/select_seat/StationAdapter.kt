package com.englizya.select_seat

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.BookingOffice
import com.englizya.model.model.LineStationTime
import com.englizya.select_seat.databinding.CardViewOfficeBinding

class StationAdapter(
    private var stations: List<LineStationTime>,
    private val onItemClicked: (LineStationTime) -> Unit,
) : RecyclerView.Adapter<StationAdapter.OfficeViewHolder>() {
    private var selectedOffice: LineStationTime? = null

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {
        holder.updateUI(stations[position])
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    fun setOffice(list: ArrayList<LineStationTime>) {
        stations = list
        notifyDataSetChanged()
    }

    inner class OfficeViewHolder(private val binding: CardViewOfficeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(stationTime: LineStationTime) {
            binding.officeName.text = stationTime.bookingOffice?.officeName

            binding.time.text = TimeOnly.map(stationTime.startTime)
            binding.root.setOnClickListener {
                onItemClicked(stationTime)
            }
            if (selectedOffice == stationTime) {
                binding.officeLogo.setImageResource(R.drawable.ic_selected_station)
            } else {
                binding.officeLogo.setImageResource(R.drawable.ic_unselected_station)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeViewHolder {
        val binding = CardViewOfficeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OfficeViewHolder(binding = binding)
    }

    fun setSelectedOffice(office: LineStationTime) {
        selectedOffice = office
        notifyDataSetChanged()
    }
}
