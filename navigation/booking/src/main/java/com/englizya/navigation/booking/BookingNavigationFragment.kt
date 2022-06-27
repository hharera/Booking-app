package com.englizya.navigation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.booking.databinding.FragmentBookingBinding
import com.englizya.common.base.BaseFragment
import com.englizya.feature.ticket.navigation.booking.R
import com.englizya.feature.ticket.navigation.booking.databinding.FragmentBookingNavigationBinding
import com.englyzia.booking.BookingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookingNavigationFragment :BaseFragment(){
    private lateinit var bind: FragmentBookingNavigationBinding
    private lateinit var navController: NavController
    private val bookingViewModel: BookingViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentBookingNavigationBinding.inflate(layoutInflater)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        navController = activity?.let { Navigation.findNavController(it, R.id.navView) }!!
        setupObservers()

    }
    private fun setupObservers() {

        bookingViewModel.exception.observe(viewLifecycleOwner) {
        }
    }
}





