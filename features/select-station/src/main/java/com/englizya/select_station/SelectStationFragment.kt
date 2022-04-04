package com.englizya.select_station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.englizya.common.base.BaseFragment
import com.englizya.select_station.databinding.FragmentSelectStationBinding
import com.englyzia.booking.BookingViewModel

class SelectStationFragment : BaseFragment() {

    private val bookingViewModel: BookingViewModel by activityViewModels()
    private lateinit var binding: FragmentSelectStationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectStationBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }
}