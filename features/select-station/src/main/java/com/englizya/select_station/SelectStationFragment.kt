package com.englizya.select_station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Trip
import com.englizya.select_station.databinding.FragmentSelectStationBinding
import com.englyzia.booking.BookingViewModel

class SelectStationFragment : BaseFragment() {

    private val bookingViewModel: BookingViewModel by activityViewModels()
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

        binding.next.setOnClickListener {
            progressToSelectSeats()
        }
    }

    private fun progressToSelectSeats() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.SELECT_SEAT,
                null
            )
        )
    }

    private fun setupObservers() {
        bookingViewModel.trip.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(trip: Trip) {
        val stationAdapter = StationAdapter(trip.line.stations) {

        }

        binding.stations.adapter = stationAdapter
    }

    override fun onResume() {
        super.onResume()
    }
}