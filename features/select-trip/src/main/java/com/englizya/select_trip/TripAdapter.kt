package com.englizya.select_trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astritveliu.boom.utils.BoomUtils
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.model.model.Station
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.CardViewTripBinding

class TripAdapter(
    private var trips: List<Trip>,
    private val source: Station?,
    private val destination: Station?,
    private val onItemClicked: (Trip) -> Unit,
    private val onOfficeClicked: (LineStationTime) -> Unit,
    private val selectedOfficeId: Int = 0,
    private var selectedStationTime: LineStationTime? = null,
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
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    fun setTrips(list: List<Trip>) {
        trips = (list)
        notifyDataSetChanged()
    }

    fun setOffice(office: LineStationTime?) {
        selectedStationTime = office
    }

    inner class NavigationItemViewHolder(private val binding: CardViewTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(trip: Trip, source: Station?, destination: Station?) {
            updateStopStationsUI(trip.tripTimes)
            updateUI(trip.tripTimes.firstOrNull())

            setTripDate(trip.reservations.first().date)

            binding.source.text = source?.branchName
            binding.sourceTimeTV.text = trip.tripTimes.firstOrNull {
                it.areaId == source?.branchId
            }?.let {
                TimeOnly.map(it.startTime)
            }

            binding.destination.text = destination?.branchName
            binding.destinationTimeTV.text = trip.tripTimes.firstOrNull {
                it.areaId == destination?.branchId
            }?.let {
                TimeOnly.map(it.startTime)
            }

            binding.price.text = trip.plan?.seatPrices?.firstOrNull {
                it.source == source?.branchId && it.destination == destination?.branchId
            }?.vipPrice.toString()

            binding.serviceDegree.text = trip.service?.serviceDegreeName

            boomBook(trip)
        }

        private fun updateStopStationsUI(tripTimes: List<LineStationTime>) {
            BookingOfficeAdapter(
                tripTimes,
                selectedOfficeId
            ) {
                onOfficeClicked(it)
                updateUI(it)
            }.let {
                binding.stations.adapter = it
            }
        }

        private fun updateUI(stationTime: LineStationTime?) {
            stationTime?.let {
                binding.station.text = stationTime.bookingOffice?.officeName
                binding.ridingTime.text = TimeOnly.map(stationTime.startTime)
                binding.exitTime.text = TimeOnly.map(stationTime.startTime)
            }
        }

        private fun boomBook(trip: Trip) {
            BoomUtils.boomAll(binding.book)

            binding.book.setOnClickListener {
                onItemClicked(trip)
            }
        }

        private fun setTripDate(date: String?) {
            binding.tripDate.text = date?.let { DateOnly.toMonthDate(it) }
        }
    }
}
