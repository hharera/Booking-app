package com.englizya.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Offer
import com.englizya.offers.databinding.FragmentOffersBinding
import com.englizya.repository.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel


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
        binding = FragmentOffersBinding.inflate(inflater, container, false)
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
                offer.offerId
            )
        )
    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }

        offersViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        offersViewModel.getOffers().observe(viewLifecycleOwner) { resource ->
            handleResult(resource)
        }
    }

    private fun handleResult(resource: Resource<List<Offer>>) {
        when (resource) {
            is Resource.Success -> {
                handleLoading(false)
                updateUI(resource.data!!)
            }

            is Resource.Loading -> {
                handleLoading(true)
            }

            is Resource.Error -> {
                handleFailure(resource.error)
            }
        }
    }

    private fun updateUI(data: List<Offer>) {
        adapter.setOffers(data)
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        binding.offerSwipeLayout.removeAllViews()
        super.onDestroyView()
    }
}