package com.englyzia.booking_payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.mapper.DateStringMapper
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Seat
import com.englyzia.booking.BookingViewModel
import com.englyzia.booking.BookingViewModel.Companion.ACCEPT_PAYMENT_REQUEST
import com.englyzia.booking_payment.databinding.FragmentBookingPaymentBinding
import com.paymob.acceptsdk.IntentConstants
import com.paymob.acceptsdk.PayActivity
import com.paymob.acceptsdk.PayActivityIntentKeys.*
import com.paymob.acceptsdk.PayResponseKeys
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookingPaymentFragment : BaseFragment() {

    private lateinit var binding: FragmentBookingPaymentBinding
    private val bookingViewModel: BookingViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.SELECT_SEAT,
                    false
                )
            )
        }

        binding.back.setOnClickListener {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.SELECT_SEAT,
                    false
                )
            )
        }

        binding.pay.setOnClickListener {
            bookingViewModel.paymentToken.value?.let {
                progressToPayment(it.token)
            }
        }
    }

    private fun progressToPayment(paymentKey: String) {
        Intent(context, PayActivity::class.java).apply {
            putExtra(PAYMENT_KEY, paymentKey)
            putExtra(THREE_D_SECURE_ACTIVITY_TITLE, getString(R.string.payment_checkout))
            putExtra(SAVE_CARD_DEFAULT, false)
            putExtra(SHOW_SAVE_CARD, false)
            putExtra(THEME_COLOR, context?.getColor(R.color.blue_600))
            putExtra("ActionBar", true)
            putExtra("language", "ar")

            startActivityForResult(this, ACCEPT_PAYMENT_REQUEST)
        }
    }

    private fun setupObservers() {
        bookingViewModel.total.observe(viewLifecycleOwner) {
            binding.totalTV.text = it.toString()
        }

        bookingViewModel.selectedSeats.observe(viewLifecycleOwner) {
            binding.ticketsCountTV.text = it.size.toString()
        }

        bookingViewModel.date.observe(viewLifecycleOwner) {
            binding.dateTV.text = DateStringMapper.map(it)
        }

        bookingViewModel.source.observe(viewLifecycleOwner) {
            binding.sourceTimeTV.text = it.branchName
        }

        bookingViewModel.destination.observe(viewLifecycleOwner) {
            binding.destinationTimeTV.text = it.branchName
        }
    }

    private fun updateUI(data: List<Seat>) {
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: $data")
        if (null == data)
            return

        if (null == data.extras)
            return

        val extras: Bundle = data.extras!!
        if (requestCode == BookingViewModel.ACCEPT_PAYMENT_REQUEST) {
            when (resultCode) {

                IntentConstants.USER_CANCELED -> {
                    showToast(R.string.user_canceled)
                }

                IntentConstants.MISSING_ARGUMENT -> {
                    showToast(R.string.missing_argument)
                }

                IntentConstants.TRANSACTION_ERROR -> {
                    showToast(R.string.transaction_error)
                }

                IntentConstants.TRANSACTION_REJECTED -> {
                    showToast(R.string.transaction_rejected)
                }

                IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE -> {
                    showToast(R.string.transaction_rejected)
                }

                IntentConstants.TRANSACTION_SUCCESSFUL -> {
                    showToast(R.string.successful_transaction)

                    extras.getString(PayResponseKeys.SUCCESS)
                    extras.getString(PayResponseKeys.ID)

                    lifecycleScope.launch {
                        bookingViewModel.submitBooking()
                    }
                }

                IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE -> {
                    showToast(R.string.successful_transaction)

                    lifecycleScope.launch {
                        bookingViewModel.submitBooking()
                    }
                }

                IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED -> {
                }

                IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION -> {
                    showToast(R.string.user_canceled)
                }

                IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE -> {
                    showToast(R.string.user_canceled)
                }

            }
        }
    }

}