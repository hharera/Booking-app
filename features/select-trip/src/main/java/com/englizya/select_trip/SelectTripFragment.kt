package com.englizya.select_trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Trip
import com.englizya.select_trip.databinding.FragmentSelectTripBinding
import com.englyzia.booking.BookingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectTripFragment : BaseFragment() {

    private lateinit var binding: FragmentSelectTripBinding
    private lateinit var adapter: TripAdapter
    private val bookingViewModel: BookingViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectTripBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
        setupUI()

        lifecycleScope.launch {
            bookingViewModel.searchTrips()
        }
    }

    private fun setupUI() {
        adapter = TripAdapter(
            emptyList(),
            bookingViewModel.source.value,
            bookingViewModel.destination.value
        ) {
            progressToSelectSeats(it)
        }

        binding.trips.adapter = adapter
    }

    private fun setupObservers() {
        bookingViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        bookingViewModel.trips.observe(viewLifecycleOwner) {

        }

        bookingViewModel.trips.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(list: List<Trip>) {
        if (list.isEmpty()) {
            binding.emptyView?.root?.visibility = View.VISIBLE
        } else {
            binding.emptyView?.root?.visibility = View.GONE
            adapter.setTrips(list)
        }
    }

    private fun progressToSelectStation(trip: Trip) {
        bookingViewModel.setSelectedTrip(trip)

        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.SELECT_STATION,
                    null
                )
            )
    }

    private fun progressToSelectSeats(trip: Trip) {
        bookingViewModel.setSelectedTrip(trip)

        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.SELECT_SEAT,
                    false
                )
            )
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.from.text = bookingViewModel.source.value?.branchName
        binding.to.text = bookingViewModel.destination.value?.branchName
    }
}