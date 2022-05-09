package com.englizya.select_station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Trip
import com.englizya.select_station.databinding.FragmentSelectStationBinding
import com.englyzia.booking.BookingViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectStationFragment : BaseFragment() {

    private val bookingViewModel: BookingViewModel by sharedViewModel()
    private lateinit var binding: FragmentSelectStationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectStationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.pay.setOnClickListener {
            progressToSelectSeats()
        }
    }

    private fun progressToSelectSeats() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.SELECT_SEAT,
                false
            )
        )
    }

    private fun setupObservers() {
        bookingViewModel.bookingOffice.observe(viewLifecycleOwner) {
            binding.pay.isEnabled = it != null
        }

        bookingViewModel.selectedTrip.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(trip: Trip) {
        val stationAdapter = StationAdapter(trip.tripTimes) {
            bookingViewModel.setSelectedBookingOffice(it)
        }

        binding.stations.adapter = stationAdapter
    }

    override fun onStart() {
        super.onStart()
        bookingViewModel.setSelectedBookingOffice(null)
    }
}