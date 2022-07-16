package com.englizya.offers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Offer
import com.englizya.offers.databinding.FragmentOffersBinding


class OffersFragment : BaseFragment() {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var adapter: OfferAdapter
    private val offersViewModel: OffersViewModel by viewModel()

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
        offersViewModel.getOffers(false)
        setupListeners()
        setupObservers()
        setupUI()

    }

    private fun setupUI(){
        adapter = OfferAdapter(
            emptyList(),
            onItemClicked = {
                navigateToOfferDetails(it)
            }
        )
        binding.offersRecyclerView.adapter = adapter

    }

    private fun navigateToOfferDetails(offer: Offer) {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.OFFER_DETAILS,
                offer.offerId.toString()
            )
        )
    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }


        offersViewModel.loading.observe(viewLifecycleOwner){
            handleLoading(it)
        }
        offersViewModel.offers.observe(viewLifecycleOwner) {
            if(it != null){
                adapter.setOffers(it)

            }
            Log.d("offers", it.toString())
        }
    }

    private fun setupListeners() {
     binding.back.setOnClickListener{
         findNavController().popBackStack()
     }
         binding.offerSwipeLayout.setOnRefreshListener {
             offersViewModel.getOffers(true)
             binding.offerSwipeLayout.isRefreshing = false
         }
    }

    override fun onDestroyView() {
        binding.offerSwipeLayout.removeAllViews()
        super.onDestroyView()
    }

}