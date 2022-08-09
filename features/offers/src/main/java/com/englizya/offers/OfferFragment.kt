package com.englizya.offers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.date.DateOnly
import com.englizya.model.model.Offer
import com.englizya.offers.databinding.FragmentOfferDetailsBinding
import com.englizya.repository.utils.Resource
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment : BaseFragment() {

    private lateinit var binding: FragmentOfferDetailsBinding
    private val offersViewModel: OffersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOfferDetailsBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

        arguments?.getString("offerId")?.let {
            getOffer(it.toInt())
        }
    }

    private fun getOffer(toInt: Int) {
        offersViewModel
            .getOffer(toInt)
            .observe(viewLifecycleOwner) {
                handleResult(it)
            }
    }

    private fun updateUI(offer: Offer) {
        Log.d(TAG, "updateUI: $offer")
        Picasso.get().load(offer.offerImageUrl).into(binding.offerImg)
        binding.offerDetails.text = offer.offerDescription
        binding.offerTitle.text = offer.offerTitle
        binding.offerStartDate.text =
            getString(R.string.offerStartDate).plus(" ").plus(DateOnly.toMonthDate(offer.startDate))
        binding.offerEndDate.text =
            getString(R.string.offerEndDate).plus(" ").plus(DateOnly.toMonthDate(offer.endDate))
        binding.offerDiscount.text =
            getString(R.string.offerDiscount).plus(" ").plus(offer.discount.toString())
    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }
        offersViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
    }

    private fun handleResult(resource: Resource<Offer>) {
        when (resource) {
            is Resource.Success -> {
                updateUI(resource.data!!)
                handleLoading(false)
            }

            is Resource.Error -> {
                handleLoading(false)
                handleFailure(resource.error)
            }

            is Resource.Loading -> {
                handleLoading(true)
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}