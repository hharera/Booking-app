package com.englizya.select_seat

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.common.utils.time.TimeOnly
import com.englizya.model.model.Seat
import com.englizya.model.model.Trip
import com.englizya.select_seat.databinding.FragmentSelectSeatBinding
import com.englyzia.booking.BookingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectSeatFragment : BaseFragment() {

    private lateinit var binding: FragmentSelectSeatBinding
    private val bookingViewModel: BookingViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectSeatBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun insertSeatViews(seatList: List<Seat>) {
        val iterator = seatList.iterator()

        for (position in 0..64) {
            val image = TextView(context).apply {
                setTextColor(resources.getColor(R.color.white))
                gravity = Gravity.CENTER
                textSize = 14f
            }

            binding.seats.addView(image)

            when (position) {
                in (0..4) -> {
                    if (position % 5 == 0) {
                        image.setBackgroundResource(R.drawable.ic_driver_steering_wheel)
                    } else if (position % 5 == 4) {
                        image.setBackgroundResource(R.drawable.ic_exit)
                    }
                }

                in (5..59) -> {
                    if (position % 5 != 2) {
                        updateSeatView(image, iterator.next())
                    }
                }
                else -> {
                    updateSeatView(image, iterator.next())
                }
            }
        }
    }

    private fun updateSeatView(image: TextView, seat: Seat) {
        var isSelected = false
        var isAvailable = true

        image.text = seat.seatId.toString()
        when (seat.seatStatus) {
            in arrayListOf("WFR", "WPR", "OFR", "OHR") -> {
                image.setBackgroundResource(R.drawable.ic_seat_booked)
                isAvailable = false
            }

            in arrayListOf("SUS", "UAV") -> {
                image.setBackgroundResource(R.drawable.ic_seat_suspend)
                isAvailable = false
            }

            "AVL" -> {
                image.setBackgroundResource(R.drawable.ic_seat_available)
            }

            else -> {
                image.setBackgroundResource(R.drawable.ic_seat_available)
            }
        }

        image.setOnClickListener {
            if (isAvailable) {
                bookingViewModel.setSelectedSeat(seat)

                if (isSelected) {
                    image.setBackgroundResource(R.drawable.ic_seat_available)
                } else {
                    image.setBackgroundResource(R.drawable.ic_seat_selected)
                }

                isSelected = isSelected.not()
            }
        }

        image.apply {
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingViewModel.clearSelectSeats()

        setupObservers()
        setupListeners()
    }

    private fun updateTimeUI(trip: Trip) {
        bookingViewModel.destination.value?.let { station ->
            trip.tripTimes.firstOrNull {
                station.branchId == it.areaId
            }?.startTime.let {
                binding.destinationTimeTV.text = TimeOnly.map(it)
            }
        }

        bookingViewModel.source.value?.let { station ->
            trip.tripTimes.firstOrNull {
                station.branchId == it.areaId
            }?.startTime.let {
                binding.sourceTimeTV.text = TimeOnly.map(it)
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.submit.setOnClickListener {
            lifecycleScope.launch {
                bookingViewModel.requestReservation()
            }
        }
    }

    private fun setupObservers() {
        bookingViewModel.source.observe(viewLifecycleOwner) { branch ->
            bookingViewModel.selectedTrip.value?.tripTimes?.firstOrNull {
                it.areaId == branch.branchId
            }?.let {
                binding.sourceTimeTV.text = it.startTime
            }
        }

        bookingViewModel.destination.observe(viewLifecycleOwner) { branch ->
            bookingViewModel.selectedTrip.value?.tripTimes?.firstOrNull {
                it.areaId == branch.branchId
            }?.let {
                binding.destinationTimeTV.text = it.startTime
            }
        }

        bookingViewModel.total.observe(viewLifecycleOwner) {
            binding.price.text = it.toString()
        }

        bookingViewModel.selectedTrip.observe(viewLifecycleOwner) {
            updateUI(it)
            updateTimeUI(it)
        }

        lifecycleScope.launch {
            bookingViewModel.reservationOrder.collect {
                it?.let {
                    progressToPayment()
                }
            }
        }

        bookingViewModel.selectedSeats.observe(viewLifecycleOwner) {
            binding.submit.isEnabled = it.isNotEmpty()
        }

        bookingViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

    }


    private fun progressToPayment() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.PAYMENT,
                false
            ),
        )
    }

    private fun updateUI(date: String?) {
        date?.let {
            DateOnly.toMonthDate(it).let {
                binding.tripDate.text = it
            }
        }
    }

    private fun updateUI(trip: Trip) {
        trip.reservations.firstOrNull()?.seats?.let {
            insertSeatViews(it)
            updateUI(date = trip.reservations.firstOrNull()?.date)
        }
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        binding.source.text = bookingViewModel.source.value?.branchName

        binding.destination.text = bookingViewModel.destination.value?.branchName
    }

}