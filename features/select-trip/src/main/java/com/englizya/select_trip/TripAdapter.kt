package com.englizya.select_trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.astritveliu.boom.utils.BoomUtils
import com.englizya.common.compose.theme.PrimaryColor
import com.englizya.common.compose.theme.White
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.LineStationTime
import com.englizya.model.model.Station
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.CardViewTripBinding

@OptIn(ExperimentalMaterialApi::class)
class TripAdapter(
    private var trips: List<Trip>,
    private val source: Station?,
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
            updateStopStationsUI(trip.tripTimes)

            setTripDate(trip.reservations.first().date)

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

        private fun updateStopStationsUI(tripTimes: ArrayList<LineStationTime>) {
            binding.stations.apply {
                setContent {
                    Column {
                        tripTimes.forEach {

                            Card {
                                var isClicked by remember { mutableStateOf(false) }

                                Card(
                                    backgroundColor = if (isClicked)
                                        PrimaryColor
                                    else
                                        Color.White,
                                    onClick = {
                                        isClicked = true
                                    },
                                ) {
                                    it.bookingOffice?.officeName?.let { officeName ->
                                        Text(
                                            text = officeName,
                                            color = if (isClicked)
                                                White
                                            else
                                                PrimaryColor,
                                        )
                                    }
                                }
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
            binding.tripDate.text = date?.let { DateOnly.toMonthDate(it) }
        }
    }
}
