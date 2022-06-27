package com.englizya.home_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.home_screen.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
//    private lateinit var adapter: OfferAdapter
    private lateinit var offerSliderAdapter: OfferSliderAdapter

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
        setupUI()
    }
    private fun setupUI(){
        offerSliderAdapter = OfferSliderAdapter(
            emptyList(),
        )
        binding.imageSlider.setSliderAdapter(offerSliderAdapter)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.imageSlider.startAutoCycle()
    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }

        homeViewModel.user.observe(viewLifecycleOwner) {
            binding.userNameTV.text = it.name
        }

        homeViewModel.offers.observe(viewLifecycleOwner) {
            if(it != null){
                offerSliderAdapter.setOffers(it)

            }
            Log.d("offers", it.toString())
        }
    }

    private fun setupListeners() {
        binding.navigationMenu.setOnClickListener {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.PROFILE, false)
            )
        }
        binding.shortTransportationService.setOnClickListener {
            progressToHomeActivity()
        }

        binding.longTransportationService.setOnClickListener {
            progressToBookingActivity()
        }

        binding.offerSeeMore.setOnClickListener {
            progressToOffers()
        }

    }

    private fun progressToOffers() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.OFFERS, false)
        )    }

    private fun progressToBookingActivity() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.BOOKING, false)
        )
    }

    private fun progressToHomeActivity() {

    }
}