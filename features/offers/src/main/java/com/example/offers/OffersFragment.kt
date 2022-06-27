package com.englizya.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.offers.R
import com.englizya.offers.databinding.FragmentOffersBinding

class OffersFragment : BaseFragment() {

    private lateinit var binding: FragmentOffersBinding
//    //    private lateinit var adapter: OfferAdapter
//    private lateinit var offerSliderAdapter: OfferSliderAdapter

//    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOffersBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}