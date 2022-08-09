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
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentOfferDetailsBinding
    private val offersViewModel: OffersViewModel by viewModel()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("offerId").let {
            offersViewModel.offerId.value = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.visibility = View.INVISIBLE

        setupListeners()
        setupObservers()

    }

    private fun updateUI(offer: Offer) {
        Picasso.get().load(offer.offerImageUrl).into(binding.offerImg)
        binding.offerDetails.text = offer.offerDescription
        binding.offerTitle.text = offer.offerTitle
        binding.offerStartDate.text = getString(R.string.offerStartDate).plus(" ").plus(DateOnly.toMonthDate(offer.startDate))
        binding.offerEndDate.text = getString(R.string.offerEndDate).plus(" ").plus(DateOnly.toMonthDate(offer.endDate))
        binding.offerDiscount.text = getString(R.string.offerDiscount).plus(" ").plus(offer.discount.toString())


    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }
        offersViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        offersViewModel.offersDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.root.visibility = View.VISIBLE
                updateUI(it)
            }
            Log.d("offer", it.toString())
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onResume() {
        super.onResume()
        offersViewModel.getOfferDetails(arguments?.get("offerId").toString())
    }
}