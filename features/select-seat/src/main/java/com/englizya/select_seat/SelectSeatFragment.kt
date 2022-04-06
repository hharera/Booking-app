package com.englizya.select_seat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.Trip
import com.englizya.select_seat.databinding.FragmentSelectSeatBinding
import com.englyzia.booking.BookingViewModel
import hilt_aggregated_deps._com_englizya_common_base_BaseViewModel_HiltModules_BindsModule

class SelectSeatFragment : BaseFragment() {

    private lateinit var binding: FragmentSelectSeatBinding
    private var adapter: SeatAdapter? = null
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
        bookingViewModel.source.observe(viewLifecycleOwner) { branch ->
            bookingViewModel.trip.value?.tripTimes?.firstOrNull {
                it.branchId == branch.branchId
            }?.let {
                binding.sourceTimeTV.text = it.startDate
            }
        }

        bookingViewModel.total.observe(viewLifecycleOwner) {
            binding.price.text = it.toString()
        }

        bookingViewModel.trip.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(trip: Trip) {
        adapter = trip.reservation?.firstOrNull()?.seats?.let {
            SeatAdapter(it) {
                bookingViewModel.setSelectedSeat(it)
            }
        }

        binding.seats.layoutManager = GridLayoutManager(context, 5)
        binding.seats.adapter = adapter
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