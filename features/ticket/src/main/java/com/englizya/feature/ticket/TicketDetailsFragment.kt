package com.englizya.feature.ticket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.ui.ColoredQr
import com.englizya.common.utils.date.DateOnly
import com.englizya.common.utils.time.TimeOnly
import com.englizya.feature.ticket.databinding.FragmentTicketDetailsBinding
import com.englizya.model.model.User
import com.englizya.model.response.UserTicket
import com.englizya.repository.utils.Resource
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
    ): View {
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
        binding.root.visibility = View.INVISIBLE

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
            binding.root.visibility = View.VISIBLE
            updateUI(it)
        }
//        ticketDetailsViewModel.user.observe(viewLifecycleOwner){resource->
//            when (resource){
//                is Resource.Error->{
//                    handleFailure(resource.error)
//                }
//                is Resource.Success->{
//                    handleLoading(false)
//                    updateUI(resource.data)
//
//                }
//                is Resource.Loading->{
//                    handleLoading(true)
//                }
//            }
//        }
    }
    private  fun updateUI(ticket: UserTicket?) {
        if (ticket != null) {
            ticket.let {
                ColoredQr().generateQRCode(it.ticketQr , it.isActive)
                    .also {
                        binding.ticketDetailQr.setImageBitmap(it)
                    }
            }
            binding.tripId.text = ticket.tripId.toString()
            binding.date.text = DateOnly.toMonthDate(ticket.reservationDate)
            binding.source.text = ticket.source
            binding.destination.text = ticket.destination
            binding.seatNo.text = ticket.seatNo.toString()
            binding.serviceType.text = ticket.serviceType
            binding.bookingOfficeName.text = ticket.bookingOfficeName

            binding.ticketTime.text = TimeOnly.map(ticket.ticketingTime)
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