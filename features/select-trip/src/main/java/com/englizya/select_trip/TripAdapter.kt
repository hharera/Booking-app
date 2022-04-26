package com.englizya.select_trip

import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.astritveliu.boom.utils.BoomUtils
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.BookingOffice
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
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    fun setTrips(list: List<Trip>) {
        trips = emptyList()
        notifyItemRemoved(0)
        trips = trips.plus(list)
        notifyDataSetChanged()
    }

    inner class NavigationItemViewHolder(private val binding: CardViewTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(trip: Trip, source: Station?, destination: Station?) {
            trip.tripTimes.let { source?.let { it1 -> updateUI(it1, it) } }

            setTripDate(trip.reservations.first().date)

            val sortedStations = trip.stations.sortedBy { it.stationOrder }

            binding.source.text = source?.branchName
            binding.sourceTimeTV.text = trip.tripTimes.firstOrNull {
                it.areaId == source?.areaId
            }?.let {
                TimeOnly.map(it.startTime)
            }

            binding.destination.text = destination?.branchName
            binding.destinationTimeTV.text = trip.tripTimes.firstOrNull {
                it.areaId == destination?.areaId
            }?.startTime

            binding.price.text = trip.plan?.seatPrices?.firstOrNull {
                it.source == source?.branchId && it.destination == destination?.branchId
            }?.vipPrice.toString()

            binding.serviceDegree.text = trip.serviceDegree?.serviceDegreeName

            boomBook(trip)
        }

        private fun updateUI(station: Station, tripTimes: List<LineStationTime>) {
            station.bookingOfficeList
                ?.forEach { bookingOffice ->
                    binding
                        .stations
                        .addView(createTextView(bookingOffice, tripTimes))
                }
        }

        private fun createTextView(
            bookingOffice: BookingOffice,
            tripTimes: List<LineStationTime>
        ): View {
            val params = RelativeLayout.LayoutParams(140, 78).apply {
                setMargins(4)
            }

            return TextView(
                binding.root.context,
            ).apply {
                setPadding(4)
                layoutParams = params
                setBackgroundResource(R.drawable.background_text_station)
                setTextColor(binding.root.context.getColor(R.color.colorPrimary))
                getStation(bookingOffice, tripTimes)?.let {
                    text = getStationTime(it)
                }
                gravity = CENTER
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            }
        }

        private fun boomBook(trip: Trip) {
            BoomUtils.boomAll(binding.book)

            binding.book.setOnClickListener {
                onItemClicked(trip)
            }
        }

        private fun setTripDate(date: String?) {
            binding.tripDate.text = date?.let { DateOnly.map(it) }
        }
    }



    private fun getStation(
        bookingOffice: BookingOffice,
        tripTimes: List<LineStationTime>
    ) = tripTimes
        .firstOrNull {
            bookingOffice.officeId == it.bookingOffice!!.officeId
        }

    private fun getStationTime(lineStationTime: LineStationTime): CharSequence? {
        return lineStationTime.bookingOffice?.officeName
            .plus("\n")
            .plus(TimeOnly.map(lineStationTime.startTime))
    }
}
