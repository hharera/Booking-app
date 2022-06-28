package com.englizya.offers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.englizya.common.base.BaseFragment
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
        setupListeners()
        setupObservers()
        setupUI()

    }

    private fun setupUI(){
        adapter = OfferAdapter(
            emptyList(),
        )
        binding.offersRecyclerView.adapter = adapter

    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
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

    }

}