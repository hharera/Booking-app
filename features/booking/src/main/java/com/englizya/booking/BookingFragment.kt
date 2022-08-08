package com.englizya.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.englizya.booking.databinding.FragmentBookingBinding
import com.englizya.common.base.BaseFragment
import com.englizya.common.mapper.DateStringMapper
import com.englizya.common.mapper.DateTimeMapper
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Station
import com.englizya.repository.utils.Resource
import com.englyzia.booking.BookingViewModel
import com.englyzia.booking.utils.BookingType
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookingFragment : BaseFragment() {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var destinationAdapter: ArrayAdapter<String>
    private lateinit var sourceAdapter: ArrayAdapter<String>
    private val bookingViewModel: BookingViewModel by sharedViewModel()
    private val roundDiscountDialog: RoundDiscountDialog by lazy {
        RoundDiscountDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBookingBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.blue_600)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupListeners()
        bookingViewModel.clearTripList()
    }

    private fun setupListeners() {
        activity?.onBackPressedDispatcher?.addCallback {
            activity?.viewModelStore?.clear()
        }

        binding.swap.setOnClickListener {
            bookingViewModel.swapStations()
        }

        binding.dateConstraintLayout.setOnClickListener {
            showDatePickerDialog()
        }

        binding.search.setOnClickListener {
            progressToSelectTrip()
        }

        binding.oneWayTrip.setOnClickListener {
            bookingViewModel.setBookingType(BookingType.OneWayBooking)
            updateUI(BookingType.OneWayBooking)
        }

        binding.roundTrip.setOnClickListener {
            bookingViewModel.setBookingType(BookingType.RoundBooking)
            updateUI(BookingType.RoundBooking)
        }
    }

    private fun updateUI(bookingType: BookingType) {
        when (bookingType) {
            BookingType.RoundBooking -> {
                binding.oneWayTrip.setBackgroundResource(R.drawable.background_unselected_reservation_type)
                binding.roundTrip.setBackgroundResource(R.drawable.background_selected_reservation_type)
                showRoundDiscountDialog()
            }

            BookingType.OneWayBooking -> {
                binding.roundTrip.setBackgroundResource(R.drawable.background_unselected_reservation_type)
                binding.oneWayTrip.setBackgroundResource(R.drawable.background_selected_reservation_type)
            }
        }
    }

    private fun showRoundDiscountDialog() {
        if (roundDiscountDialog.isAdded.not())
            roundDiscountDialog.show(childFragmentManager, "RoundDialog")
    }

    private fun progressToSelectTrip() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.SELECT_TRIP, false)
        )
    }

    private fun setupObservers() {
        bookingViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.search.isEnabled = it.formIsValid
        }

        bookingViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = DateStringMapper.map(it)
        }

        bookingViewModel.source.observe(viewLifecycleOwner) {
            it.branchName?.let {
                binding.source.setText(it)
            }
        }

        bookingViewModel.user.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    handleFailure(resource.error)
                    bookingViewModel.getUser(true)
                }
                is Resource.Loading -> {
                    handleLoading(true)
                }
                is Resource.Success -> {
                    handleLoading(false)

                }
            }
        }
        bookingViewModel.destination.observe(viewLifecycleOwner) {
            it.branchName?.let {
                binding.destination.setText(it)
            }
        }

        bookingViewModel.stations.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }
    }

    private fun updateUI(it: List<Station>) {
        sourceAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.card_view_station,
            R.id.station,
            it.map { it.branchName }
        )

        destinationAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.card_view_station,
            R.id.station,
            it.map { it.branchName }
        )

        binding.fromConstraintLayout.setOnClickListener {
            bookingViewModel.getStations()
            showSourceMenu(it)
        }

        binding.constraintLayout.setOnClickListener {
            bookingViewModel.getStations()
            showDestinationMenu(it)
        }
    }

    override fun onResume() {
        super.onResume()

        bookingViewModel.getBookingOffices()
    }

    private fun showDatePickerDialog() {
        val datePicker =
            MaterialDatePicker
                .Builder
                .datePicker()
                .setCalendarConstraints(
                    CalendarConstraints
                        .Builder()
                        .setStart(DateTime.now().millis)
                        .setEnd(DateTime.now().plusWeeks(1).millis)
                        .build()
                )
                .setTitleText(getString(R.string.select_trip_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            Log.d(TAG, "showDatePickerDialog: $it")
            val time = DateTimeMapper.map(it)
            bookingViewModel.setDate(time)
        }
        datePicker.show(childFragmentManager, "DATE")
    }

    private fun showDestinationMenu(view: View) {
        ListPopupWindow(context!!).apply {
            setAdapter(destinationAdapter)
            anchorView = view

            setOnItemClickListener { adapterView, view, i, l ->
                adapterView.adapter.getItem(i).toString().let {
                    bookingViewModel.setDestination(it)
                }
                dismiss()
            }

            show()
        }
    }

    private fun showSourceMenu(view: View) {
        ListPopupWindow(context!!).apply {
            setAdapter(destinationAdapter)
            anchorView = view

            setOnItemClickListener { adapterView, view, i, l ->
                adapterView.adapter.getItem(i).toString().let {
                    bookingViewModel.setSource(it)
                }
                dismiss()
            }

            show()
        }
    }
}