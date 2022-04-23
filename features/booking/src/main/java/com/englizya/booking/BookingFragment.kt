package com.englizya.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
    private lateinit var adapter: ArrayAdapter<String>
    private val bookingViewModel: BookingViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.source.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.adapter.getItem(i).toString().let {
                Log.d(TAG, "setupListeners: $it")
                bookingViewModel.setSource(it)
            }
        }

        binding.destination.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.adapter.getItem(i).toString().let {
                Log.d(TAG, "setupListeners: $it")
                bookingViewModel.setDestination(it)
            }
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
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.SELECT_TRIP, null)
        )
    }

    private fun setupObservers() {
        bookingViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.search.isEnabled = it.formIsValid

            if (null != it.sourceError) {
                binding.source.setError(getString(it.sourceError!!))
            } else {
                binding.source.error = null
            }

            if (null != it.destinationError) {
                binding.destination.setError(getString(it.destinationError!!))
            } else {
                binding.destination.error = null
            }
        }

        bookingViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = DateStringMapper.map(it)
        }

        bookingViewModel.stations.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(it: List<Station>) {
        adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.card_view_station,
            R.id.station,
            it.map { it.branchName }
        )

        binding.source.setAdapter(adapter)
        binding.destination.setAdapter(adapter)
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
}