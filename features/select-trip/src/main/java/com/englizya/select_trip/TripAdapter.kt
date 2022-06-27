@file:OptIn(ExperimentalMaterialApi::class)

package com.englizya.select_trip

import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.astritveliu.boom.utils.BoomUtils
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.model.model.Station
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.CardViewTripBinding
import com.englizya.select_trip.ui.theme.Blue500
import com.englizya.select_trip.ui.theme.Grey100
import java.util.*

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
        val sortedList = list.sortedBy { trip ->
            trip.tripTimes.firstOrNull {
                it.areaId == source?.branchId
            }?.let { TimeOnly.timeIn24TimeSystem(it.startTime) } ?: ""
        }

        trips = (sortedList)
        notifyDataSetChanged()
    }

    fun setOffice(office: LineStationTime?) {
        Log.d("trips:time", office.toString())
        selectedStationTime = office
    }

    inner class NavigationItemViewHolder(private val binding: CardViewTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(trip: Trip, source: Station?, destination: Station?) {
            updateStopStationsUI(
                trip.tripTimes.sortedBy {
                    TimeOnly.timeIn24TimeSystem(it.startTime)
                }
            )
            setTripDate(trip.reservations.first().date)
            updateTripDetails(trip)

            binding.source.text = source?.branchName
            binding.sourceTimeTV.text = trip.tripTimes.firstOrNull {
                it.areaId == source?.branchId
            }?.let {
                //  Log.d("trip", TimeOnly.map(it.startTime).toString())
                TimeOnly.map(it.startTime)
            }

            binding.destination.text = destination?.branchName
            binding.destinationTimeTV.text = trip.tripTimes.firstOrNull {
                it.areaId == destination?.branchId
            }?.let {
                TimeOnly.map(it.startTime)
            }

            binding.price.text = getTripPrice(trip).toString()

            binding.serviceDegree.text = trip.service?.serviceDegreeName

            boomBook(trip)
        }

        private fun updateTripDetails(trip: Trip) {
            binding.tripName.text = trip.tripName
            binding.tripCode.text = "#".plus(trip.tripId)
        }

        private fun getTripPrice(selectedTrip: Trip): Double? {
            return when (selectedTrip.service?.serviceId) {
                1 -> {
                    selectedTrip.let {
                        it.plan?.seatPrices?.first {
                            it.source == source?.branchId &&
                                    it.destination == destination?.branchId
                        }?.vipPrice!!
                    }!!.toDouble()
                }

                2 -> {
                    selectedTrip.let {
                        it.plan?.seatPrices?.first {
                            it.source == source?.branchId &&
                                    it.destination == destination?.branchId
                        }?.economicPrice!!
                    }!!.toDouble()
                }

                3 -> {
                    selectedTrip.let {
                        it.plan?.seatPrices?.first {
                            it.source == source?.branchId &&
                                    it.destination == destination?.branchId
                        }?.pullmanPrice!!
                    }!!.toDouble()
                }

                4 -> {
                    selectedTrip.let {
                        it.plan?.seatPrices?.first {
                            it.source == source?.branchId &&
                                    it.destination == destination?.branchId
                        }?.pullmanPrice!!
                    }!!.toDouble()
                }

                7 -> {
                    selectedTrip.let {
                        it.plan?.seatPrices?.first {
                            it.source == source?.branchId &&
                                    it.destination == destination?.branchId
                        }?.royalGoldPrice!!
                    }!!.toDouble()
                }

                8 -> {
                    selectedTrip.let {
                        it.plan?.seatPrices?.first {
                            it.source == source?.branchId &&
                                    it.destination == destination?.branchId
                        }?.miniGoldPrice!!
                    }!!.toDouble()
                }

                else -> {
                    0.0
                }
            }
        }

        private fun updateStopStationsUI(tripTimes: List<LineStationTime>) {
            binding.stations.setContent {
                var selectedBookingOffice by remember { mutableStateOf<Int?>(null) }

                LazyRow() {
                    tripTimes.forEach { stationTime ->
                        item {
                            Card(
                                backgroundColor =
                                if (stationTime.bookingOffice?.officeId == selectedBookingOffice) {
                                    Blue500
                                } else {
                                    Grey100
                                },
                                elevation = 0.dp,
                                modifier = Modifier.padding(6.dp),
                                onClick = {
                                    selectedBookingOffice = stationTime.bookingOffice?.officeId
                                    onOfficeClicked(stationTime)
                                }
                            ) {
                                Text(
                                    text = stationTime.bookingOffice?.officeName?.plus(
                                        "\n".plus(
                                            TimeOnly.map(
                                                stationTime.startTime
                                            )
                                        )
                                    ).toString(),
                                    color = if (stationTime.bookingOffice?.officeId == selectedBookingOffice) {
                                        Grey100
                                    } else {
                                        Blue500
                                    },
                                    modifier = Modifier.padding(6.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun boomBook(trip: Trip) {
            BoomUtils.boomAll(binding.book)

            binding.book.setOnClickListener {
                onItemClicked(trip)
            }
        }

        private fun setTripDate(date: String?) {
            binding.tripDate.text = date?.let { DateOnly.getTripDate(it) }
        }

        private fun setupLanguage() {
            val resources = binding.root.context.resources
            val locale = Locale.getDefault()
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            config.setLayoutDirection(locale)

            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}
