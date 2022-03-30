package com.englizya.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.booking.databinding.FragmentBookingBinding
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.BookingOffice
import kotlinx.coroutines.launch

class BookingFragment : BaseFragment() {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val bookingViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.source.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.adapter.getItem(i).toString().let {
                bookingViewModel.setSource(it)
            }
        }

        binding.destination.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.adapter.getItem(i).toString().let {
                bookingViewModel.setDestination(it)
            }
        }
    }

    private fun setupObservers() {
        bookingViewModel.bookingOffices.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(it: List<BookingOffice>) {
        adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.card_view_booking_office,
            it.map { it.officeName }
        )

        binding.source.setAdapter(adapter)
        binding.destination.setAdapter(adapter)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch { bookingViewModel.getBookingOffices() }
    }

    private fun makeOnBackground(function: () -> Unit) {
        lifecycleScope.launch {
            function.invoke()
        }
    }

}