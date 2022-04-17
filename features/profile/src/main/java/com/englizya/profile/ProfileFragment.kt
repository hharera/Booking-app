package com.englizya.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.profile.NavigationItem.*
import com.englizya.profile.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    private val navigationItemList = arrayListOf(
        UserTickets,
        ReportProblem,
        SuggestIdea,
        Settings,
        AboutUs,
        TermsAndPolicy,
//        UpcomingFeatures,
//        PaymentCards,
//        PaymentHistory,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
        setupRecyclerViewAdapter()
    }

    private fun setupObservers() {

    }

    private fun setupRecyclerViewAdapter() {
        val adapter =
            NavigationAdapter(navigationItemList) {
                checkClickItem(it)
            }

        binding.navigationMenu.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        restoreValues()
    }

    private fun restoreValues() {

    }

    private fun setupListeners() {
        binding.back.setOnClickListener {

        }
    }

    private fun checkClickItem(item: NavigationItem) {
        when (item) {
            is Settings -> {
                navigateToSettings()
            }

            is PaymentCards -> {

            }

            is Settings -> {

            }

            is TermsAndPolicy -> {

            }

            is PaymentHistory -> {

            }

            is ReportProblem -> {

            }

            is SuggestIdea -> {
                navigateToSuggestIdea()
            }

            is AboutUs -> {

            }

            is UpcomingFeatures -> {

            }
        }
    }

    private fun navigateToComplaint() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.COMPLAINT, false)
        )
    }

    private fun navigateToDriverRating() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.DRIVER_RATING, false)
        )
    }

    private fun navigateToSuggestIdea() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.SUGGEST_IDEA, false)
        )
    }


    private fun navigateToSettings() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.SETTINGS, false)
        )
    }

}