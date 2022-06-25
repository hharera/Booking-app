package com.englizya.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.booking.databinding.FragmentBookingBinding
import com.englizya.common.base.BaseFragment
import com.englizya.common.mapper.DateStringMapper
import com.englizya.common.mapper.DateTimeMapper
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Station
import com.englyzia.booking.BookingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookingFragment : BaseFragment() {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var destinationAdapter: ArrayAdapter<String>
    private lateinit var sourceAdapter: ArrayAdapter<String>
    private val bookingViewModel: BookingViewModel by sharedViewModel()

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
//        binding.source.setOnItemClickListener { adapterView, view, i, l ->
//            adapterView.adapter.getItem(i).toString().let {
//                Log.d(TAG, "setupListeners: $it")
//                bookingViewModel.setSource(it)
//            }
//        }

        binding.swap.setOnClickListener {
            bookingViewModel.swapStations()
        }

        binding.dateConstraintLayout.setOnClickListener {
            showDatePickerDialog()
        }

        binding.search.setOnClickListener {
            progressToSelectTrip()
        }
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

        lifecycleScope.launch { bookingViewModel.getBookingOffices() }
    }

    private fun showDatePickerDialog() {
        val datePicker =
            MaterialDatePicker
                .Builder
                .datePicker()
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