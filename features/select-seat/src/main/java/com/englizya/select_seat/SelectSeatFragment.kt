package com.englizya.select_seat

import android.graphics.Paint
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
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
import com.englizya.model.model.ServiceDegree
import com.englizya.model.model.Trip
import com.englizya.select_seat.BusSeats.BUS_TYPE_28_SEATS
import com.englizya.select_seat.BusSeats.BUS_TYPE_32_SEATS
import com.englizya.select_seat.BusSeats.BUS_TYPE_49_SEATS
import com.englizya.select_seat.databinding.FragmentSelectSeatBinding
import com.englyzia.booking.BookingViewModel
import com.englyzia.booking.utils.BookingType
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.time.times

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
        if (seatList.size == BUS_TYPE_49_SEATS) {
            spreadBus_49_SeatsView(seatList)
        } else if (seatList.size == BUS_TYPE_32_SEATS) {
            spreadMiniBus_32_SeatsView(seatList)
        } else if (seatList.size == BUS_TYPE_28_SEATS) {
            spreadMiniBus_28_SeatsView(seatList)
        }
    }

    private fun spreadMiniBus_32_SeatsView(seatList: List<Seat>) {
        val iterator = seatList.iterator()

        for (position in 0..44) {
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
                    } else if (position % 5 == 3) {
                        updateSeatView(image, iterator.next())
                    } else if (position % 5 == 4) {
                        updateSeatView(image, iterator.next())
                    }
                }

                in (5..9) -> {
                    if (position % 5 == 0) {
                        updateSeatView(image, iterator.next())
                    } else if (position % 5 == 1) {
                        updateSeatView(image, iterator.next())
                    } else if (position % 5 == 4) {
                        image.setBackgroundResource(R.drawable.ic_door)
                    }
                }

                in (10..40) -> {
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

    private fun spreadMiniBus_28_SeatsView(seatList: List<Seat>) {
        val iterator = seatList.iterator()

        for (position in 0..39) {
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
                        updateSeatView(image, iterator.next())
                    }
                }

                in (5..9) -> {
                    if (position % 5 == 0) {
                        updateSeatView(image, iterator.next())
                    } else if (position % 5 == 1) {
                        updateSeatView(image, iterator.next())
                    } else if (position % 5 == 4) {
                        image.setBackgroundResource(R.drawable.ic_door)
                    }
                }

                in (10..35) -> {
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

    private fun spreadBus_49_SeatsView(seatList: List<Seat>) {
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
                        image.setBackgroundResource(R.drawable.ic_door)
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

        // bookingViewModel.clearSelectSeats()

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
            parentFragmentManager.popBackStack()
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

            if (bookingViewModel.bookingType.value == BookingType.RoundBooking) {
                binding.priceBeforeDiscount.setText(
                    it.times(2).toString(),
                    TextView.BufferType.SPANNABLE
                )
                val STRIKE_THROUGH_SPAN = StrikethroughSpan()
                val spannable: Spannable = binding.priceBeforeDiscount.text as Spannable
                binding.priceBeforeDiscount.length().let { it1 ->
                    spannable.setSpan(
                        STRIKE_THROUGH_SPAN,
                        0,
                        it1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                binding.priceBeforeDiscount.text = spannable
                if (bookingViewModel.totalAfterDiscount.value != null) {
                    binding.price.text = bookingViewModel.totalAfterDiscount.value.toString()

                }
//              else{
//                  binding.price.text = 0.0.toString()
//
//              }

            } else {
                binding.priceBeforeDiscount.visibility = View.GONE
                binding.price.text = it.toString()


            }
        }

        bookingViewModel.selectedTrip.observe(viewLifecycleOwner) {
            updateUI(it)
            updateTimeUI(it)
        }

        bookingViewModel.reservationOrder.observe(viewLifecycleOwner) {
            it?.let {
                progressToPayment()
            }
        }

        bookingViewModel.selectedSeats.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.priceBeforeDiscount.visibility = View.VISIBLE

            } else {
                binding.priceBeforeDiscount.visibility = View.GONE

            }
            binding.submit.isEnabled = it.isNotEmpty()
        }

        bookingViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
//        bookingViewModel.bookingType.observe(viewLifecycleOwner) {
//            val total = bookingViewModel.total.value?.minus(0.1 * bookingViewModel.total.value!!)
//            Log.d("Total" , total.toString())
//            if (total != null) {
//                bookingViewModel.setTotal(total)
//            }
//        }

        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
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
            updateUI(trip.service)
        }
    }

    private fun updateUI(service: ServiceDegree?) {
        binding.serviceDegree.text = service?.serviceDegreeName.toString()
    }


    override fun onResume() {
        super.onResume()
        bookingViewModel.clearSelectSeats()
        restoreValues()
    }

    private fun restoreValues() {
        binding.source.text = bookingViewModel.source.value?.branchName

        binding.destination.text = bookingViewModel.destination.value?.branchName
    }

}