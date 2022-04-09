package com.englizya.select_seat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.Seat
import com.englizya.model.model.Trip
import com.englizya.select_seat.databinding.FragmentSelectSeatBinding
import com.englyzia.booking.BookingViewModel

class SelectSeatFragment : BaseFragment() {

    private lateinit var binding: FragmentSelectSeatBinding
    private val bookingViewModel: BookingViewModel by activityViewModels()

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
            val image = ImageView(context).apply {
            }

            binding.seats.addView(image)

            when (position) {
                in (0..4) -> {
                    if (position % 5 == 0) {
                        image.setImageResource(R.drawable.ic_driver_steering_wheel)
                    } else if (position % 5 == 4) {
                        image.setImageResource(R.drawable.ic_exit)
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

    private fun updateSeatView(image: ImageView, seat: Seat) {
        var isSelected = false
        var isAvailable = true

        if (seat.source != null || seat.source != null) {
            image.setImageResource(R.drawable.ic_seat_booked)
            isAvailable = false
        } else {
            image.setImageResource(R.drawable.ic_seat_available)
        }

        image.setOnClickListener {
            if (isAvailable) {
                bookingViewModel.setSelectedSeat(seat)

                if (isSelected) {
                    image.setImageResource(R.drawable.ic_seat_available)
                } else {
                    image.setImageResource(R.drawable.ic_seat_selected)
                }

                isSelected = isSelected.not()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingViewModel.clearSelectSeats()

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.book.setOnClickListener {
            bookingViewModel.book()
        }
    }

    private fun setupObservers() {
        bookingViewModel.source.observe(viewLifecycleOwner) { branch ->
            bookingViewModel.trip.value?.tripTimes?.firstOrNull {
                it.areaId == branch.branchId
            }?.let {
                binding.sourceTimeTV.text = it.startTime
            }
        }

        bookingViewModel.destination.observe(viewLifecycleOwner) { branch ->
            bookingViewModel.trip.value?.tripTimes?.firstOrNull {
                it.areaId == branch.branchId
            }?.let {
                binding.destinationTimeTV.text = it.startTime
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
        trip.reservation?.firstOrNull()?.seats?.let {
            insertSeatViews(it)
        }
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        binding.source.text = bookingViewModel.source.value?.branchName

        binding.sourceTimeTV.text = bookingViewModel.trip.value?.tripTimes?.firstOrNull {
            it.areaId == bookingViewModel.source.value?.branchId
        }?.startTime


        binding.destination.text = bookingViewModel.destination.value?.branchName

        binding.destinationTimeTV.text = bookingViewModel.trip.value?.tripTimes?.firstOrNull {
            it.areaId == bookingViewModel.destination.value?.branchId
        }?.startTime
    }
}