package com.englizya.select_seat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.Trip
import com.englizya.select_seat.databinding.FragmentSelectSeatBinding
import com.englyzia.booking.BookingViewModel

class SelectSeatFragment : BaseFragment() {

    private lateinit var binding: FragmentSelectSeatBinding
    private val bookingViewModel: BookingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectSeatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        bookingViewModel.trip.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(trip: Trip) {
//        trip.plan.seatPrices.
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        binding.source.text = bookingViewModel.source.value?.branchName
        binding.sourceTimeTV.text = bookingViewModel.trip.value?.startDate

        binding.destination.text = bookingViewModel.destination.value?.branchName
        binding.destinationTimeTV.text = bookingViewModel.trip.value?.endDate
    }
}