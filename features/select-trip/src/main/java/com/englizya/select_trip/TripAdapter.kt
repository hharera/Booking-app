package com.englizya.select_trip

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.astritveliu.boom.utils.BoomUtils
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.model.model.Station
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.CardViewTripBinding

class TripAdapter(
    private var trips: List<Trip>,
    val source: Station?,
    private val destination: Station?,
    private val onItemClicked: (Trip) -> Unit,
) : RecyclerView.Adapter<TripAdapter.NavigationItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
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

    fun setTrips(list: List<Trip>) {
        trips = trips.plus(list)
        notifyDataSetChanged()
    }

    class NavigationItemViewHolder(private val binding: CardViewTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(trip: Trip, source: Station?, destination: Station?) {
            trip.tripTimes?.let { source?.let { it1 -> updateUI(it1, it) } }

            val sortedStations = trip.line.stations.sortedBy { it.stationOrder }

            binding.source.text = source?.branchName
            binding.sourceTimeTV.text = trip.tripTimes?.firstOrNull {
                it.areaId == source?.areaId
            }?.let {
                TimeOnly.map(it.startTime)
            }

            binding.destination.text = destination?.branchName
            binding.destinationTimeTV.text = trip.tripTimes?.firstOrNull {
                it.areaId == destination?.areaId
            }?.startTime

            binding.price.text = trip.plan?.seatPrices?.firstOrNull {
                it.source == source?.branchId && it.destination == destination?.branchId
            }?.vipPrice.toString()

            binding.serviceDegree.text = trip.serviceDegree?.serviceDegreeName

            boomBook()
        }

        private fun updateUI(station: Station, tripTimes: List<LineStationTime>) {
            station.bookingOfficeList?.forEach {
                binding
                    .stations
                    .addView(
                        TextView(
                            binding.root.context,
                        ).apply {
                            text = "${it.officeName}".plus("\n")
                                .plus(
                                    tripTimes.first {
                                        it.bookingOfficeId == it.bookingOfficeId
                                    }
                                )
                            setBackgroundResource(R.drawable.background_text_station)
                            setTextColor(binding.root.context.getColor(R.color.colorPrimary))
                            setPadding(4)
                            gravity = android.view.Gravity.CENTER
                            setTextSize(
                                TypedValue.COMPLEX_UNIT_SP,
                                11f
                            )
                        }
                    )
            }
        }

        private fun boomBook() {
            BoomUtils.boomAll(binding.book)
        }
    }
}
