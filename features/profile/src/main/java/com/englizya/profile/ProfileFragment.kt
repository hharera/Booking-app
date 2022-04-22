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
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModel()

    private val navigationItemList = arrayListOf(
        UserTickets,
        PaymentHistory,
        ReportProblem,
        DriverReview,
        SuggestIdea,
        Settings,
//        AboutUs,
//        TermsAndPolicy,
//        UpcomingFeatures,
//        PaymentCards,
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
            findNavController().popBackStack()
        }

        binding.logout.setOnClickListener {
            profileViewModel.logout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        activity?.finish()
//        startActivity(
//            Intent(
//                context,
//                LoginActivity::class.java
//            )
//        )
    }

    private fun checkClickItem(item: NavigationItem) {
        when (item) {
            is Settings -> {
                navigateToSettings()
            }

            is PaymentCards -> {

            }

            is TermsAndPolicy -> {

            }

            is PaymentHistory -> {

            }

            is ReportProblem -> {
                navigateToComplaint()
            }

            is SuggestIdea -> {
                navigateToSuggestIdea()
            }

            is AboutUs -> {

            }

            is UpcomingFeatures -> {

            }

            is DriverReview -> {
                navigateReviewDriver()
            }
        }
    }

    private fun navigateToComplaint() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.COMPLAINT, false)
        )
    }

    private fun navigateToPaymentHistory() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.PAYMENT_HISTORY,
                false
            )
        )
    }

    private fun navigateReviewDriver() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, Destination.DRIVER_REVIEW, false)
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