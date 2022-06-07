package com.englizya.feature.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.feature.ticket.databinding.FragmentTicketDetailsBinding
import com.englizya.model.response.UserTicket
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentTicketDetailsBinding

    private val ticketDetailsViewModel: TicketDetailsViewModel by viewModel()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTicketDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }
    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setupObservers() {
        ticketDetailsViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        ticketDetailsViewModel.ticket.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(it: UserTicket?) {
it.let { BarcodeEncoder()
    .encodeBitmap(
        it?.ticketQr,
        BarcodeFormat.QR_CODE,
        150,
        150
    ).also {
        binding.ticketDetailQr.setImageBitmap(it)
    } }
        it?.tripId?.let { it1 -> binding.tripId.setText(it1) }
        it?.reservationDate?.let { it1 -> binding.date.setText(it1) }
        it?.source?.let { it1 -> binding.source.setText(it1) }
        it?.destination?.let { it1 -> binding.destination.setText(it1) }
        it?.uid?.let { it1 -> binding.userName.setText(it1) }
// Phone - No of Ticket -  price

    }

    override fun onResume() {
        super.onResume()
        ticketDetailsViewModel.getTicketDetails()
    }
}