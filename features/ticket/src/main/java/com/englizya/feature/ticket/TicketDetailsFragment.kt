package com.englizya.feature.ticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.time.TimeOnly
import com.englizya.feature.ticket.databinding.FragmentTicketDetailsBinding
import com.englizya.model.model.User
import com.englizya.model.response.UserTicket
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.joda.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern

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
            binding.tripId.text = ticket.tripId.toString()
            binding.date.text = DateOnly.toMonthDate(ticket.reservationDate)
            binding.source.text = ticket.source
            binding.destination.text = ticket.destination
            binding.seatNo.text = ticket.seatNo.toString()
            binding.serviceType.text = ticket.serviceType
            binding.bookingOfficeName.text = ticket.bookingOfficeName

            binding.ticketTime.text = TimeOnly.ToTime(ticket.ticketingTime).toString()


        }
    }
    private  fun updateUI(user: User?) {
        Log.d("UserInfo" , user?.phoneNumber +user?.password)
        binding.userPhoneNumber.text = user?.phoneNumber
        binding.userName.text = user?.name
    }


    override fun onResume() {
        super.onResume()
        ticketDetailsViewModel.getTicketDetails(arguments?.get("ticketId").toString())
    }
}