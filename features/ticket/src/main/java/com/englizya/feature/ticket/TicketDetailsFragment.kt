package com.englizya.feature.ticket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.feature.ticket.databinding.FragmentTicketDetailsBinding
import com.englizya.model.model.User
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("ticketId").let {
            ticketDetailsViewModel.ticketId.value = it
        }
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
        ticketDetailsViewModel.user.observe(viewLifecycleOwner){
            updateUI(it)
        }
    }

    private  fun updateUI(ticket: UserTicket?) {
        if (ticket != null) {
            ticket.let {
                BarcodeEncoder()
                    .encodeBitmap(
                        ticket.ticketQr,
                        BarcodeFormat.QR_CODE,
                        150,
                        150
                    ).also { ticket ->
                        binding.ticketDetailQr.setImageBitmap(ticket)
                    }
            }
            binding.tripId.setText(ticket.tripId)
            binding.date.setText(ticket.reservationDate)
            binding.source.setText(ticket.source)
            binding.destination.setText(ticket.destination)
            binding.seatNo.setText(ticket.seatNo)
            binding.serviceType.setText(ticket.serviceType)
            binding.bookingOfficeName.setText(ticket.bookingOfficeName)
            binding.ticketTime.setText(ticket.ticketingTime)
        }


    }
    private  fun updateUI(user: User?) {
        Log.d("UserInfo" , user?.phoneNumber +user?.password)
        binding.userPhoneNumber.setText(user?.phoneNumber)
        binding.userName.setText(user?.name)
    }


    override fun onResume() {
        super.onResume()
        ticketDetailsViewModel.getTicketDetails(arguments?.get("ticketId").toString())
    }
}