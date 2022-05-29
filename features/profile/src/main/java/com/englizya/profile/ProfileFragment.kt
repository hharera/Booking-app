package com.englizya.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.ui.QrDialog
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.User
import com.englizya.profile.NavigationItem.*
import com.englizya.profile.databinding.FragmentProfileBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: NavigationAdapter
    private val profileViewModel: ProfileViewModel by viewModel()

    private val navigationItemList = arrayListOf(
        UserTickets,
//        PaymentHistory,
        SuggestionsAndComplaint,
        DriverReview,
        Settings,
        AboutUs,
        ContactUs,
        TermsAndConditions,
        RefundPolicy,
        PrivacyPolicy,
        LogOut,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupRecyclerViewAdapter()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            profileViewModel.userBalance.collectLatest {
                binding.balance.text = it.toString()
            }
        }

        profileViewModel.user.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }
    }

    private fun updateUI(user: User) {
        BarcodeEncoder().encodeBitmap(user.uid, BarcodeFormat.QR_CODE, 48, 48).let {
            binding.profileQr.setImageBitmap(it)
        }

        binding.profileName.text = getString(R.string.profile_name, user.name)
    }

    private fun setupRecyclerViewAdapter() {
        NavigationAdapter(navigationItemList) {
            checkClickItem(it)
        }.let { navigationAdapter ->
            adapter = navigationAdapter
            binding.navigationMenu.adapter = navigationAdapter
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.charge.setOnClickListener {
            navigateToRecharging()
        }

        binding.profileQr.setOnClickListener {
            viewQrDialog()
        }
    }

    private fun viewQrDialog() {
        QrDialog(
            profileViewModel.user.value,
        ).show(
            childFragmentManager,
            "QrDialog"
        )
    }

    private fun navigateToLogin() {
        kotlin.runCatching {
            Class.forName("com.englizya.navigation.login.LoginActivity").let {
                Intent(context, it).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(this)
                }
            }
        }
    }

    private fun checkClickItem(item: NavigationItem) {
        when (item) {
            is UserTickets -> {
                navigateToUserTickets()
            }

            is Settings -> {
                navigateToSettings()
            }

            is PaymentCards -> {

            }

            is TermsAndConditions -> {
                navigateToTermsAndConditions()
            }

            is PaymentHistory -> {

            }

            is SuggestionsAndComplaint -> {
                navigateToComplaint()
            }

            is SuggestIdea -> {
                navigateToSuggestIdea()
            }

            is AboutUs -> {
                navigateToAboutUs()
            }

            is UpcomingFeatures -> {

            }

            is PrivacyPolicy -> {
                navigateToPrivacyPolicy()
            }

            is ContactUs -> {
                navigateToContactUs()
            }

            is DriverReview -> {
                navigateReviewDriver()
            }

            is RefundPolicy -> {
                navigateRefundPolicy()
            }

            is LogOut -> {
                profileViewModel.logout()
                navigateToLogin()
            }
        }
    }

    private fun navigateRefundPolicy() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.REFUND_POLICY,
                    false
                )
            )
    }

    private fun navigateToPrivacyPolicy() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.PRIVACY_POLICY,
                    false
                )
            )
    }

    private fun navigateToAboutUs() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.AboutUs,
                    false
                )
            )
    }

    private fun navigateToContactUs() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.ContactUs,
                    false
                )
            )
    }

    private fun navigateToTermsAndConditions() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.TERMS_AND_CONDITIONS,
                    false
                )
            )
    }

    private fun navigateToUserTickets() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                     Domain.ENGLIZYA_PAY,
                    Destination.USER_TICKETS,
                    false
                )
            )
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

    private fun navigateToRecharging() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.RECHARGING,
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