package com.englyzia.booking_payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.mapper.DateStringMapper
import com.englyzia.booking.BookingViewModel
import com.englyzia.booking.utils.PaymentMethod
import com.englyzia.booking_payment.databinding.FragmentBookingPaymentBinding
import com.harera.user_tickets.UserTicketsActivity
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkError
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookingPaymentFragment : BaseFragment(), CallbackPaymentInterface {

    private lateinit var binding: FragmentBookingPaymentBinding
    private val bookingViewModel: BookingViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBookingPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        bookingViewModel.setSelectedPaymentMethod(PaymentMethod.Card)
    }

    private fun setupListeners() {
        requireActivity().onBackPressedDispatcher.addCallback {
            activity?.finish()
        }

        binding.back.setOnClickListener {
            activity?.finish()
        }

        binding.pay.setOnClickListener {
            bookingViewModel.whenPayButtonClicked()
        }

        binding.cardMethodCL.setOnClickListener {
            bookingViewModel.setSelectedPaymentMethod(PaymentMethod.Card)
            updateSelectedMethodUI(it.id)
        }

        binding.englizyaWalletCL.setOnClickListener {
            bookingViewModel.setSelectedPaymentMethod(PaymentMethod.EnglizyaWallet)
            updateSelectedMethodUI(it.id)
        }

        binding.pay.setOnClickListener {
            bookingViewModel.whenPayButtonClicked()
        }
    }

    private fun updateSelectedMethodUI(id: Int) {
        binding.methodsGL.forEach {
            if (it.id == id) {
                it.setBackgroundResource(R.drawable.background_button_payment_method_selected)
            } else {
                it.setBackgroundResource(R.drawable.background_button_payment_method)
            }
        }
    }

//    private fun payWithPayMob(paymentKey: String) {
//        Intent(context, PayActivity::class.java).apply {
//            putExtra(PAYMENT_KEY, paymentKey)
//            putExtra(THREE_D_SECURE_ACTIVITY_TITLE, getString(R.string.payment_checkout))
//            putExtra(SAVE_CARD_DEFAULT, false)
//            putExtra(SHOW_SAVE_CARD, false)
//            putExtra(THEME_COLOR, context?.getColor(R.color.blue_600))
//            putExtra("ActionBar", true)
//            putExtra("language", "ar")
//
//            startActivityForResult(this, ACCEPT_PAYMENT_REQUEST)
//        }
//    }

    private fun setupObservers() {
        activity?.onBackPressedDispatcher?.addCallback {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            bookingViewModel.billingDetails.collect {
                it?.let {
                    payWithPayTabs(it)
                }
            }
        }

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

        bookingViewModel.onlineTickets.observe(viewLifecycleOwner) {
            showUserTickets()
        }


        bookingViewModel.reservationTickets.observe(viewLifecycleOwner) {
            showUserTickets()
        }

        bookingViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        bookingViewModel.reservationWithWalletRequest.observe(viewLifecycleOwner) {
            bookingViewModel.confirmReservation(it)
        }

    }

    private fun showUserTickets() {
        requireActivity().apply {
            startActivity(
                Intent(this, UserTicketsActivity::class.java)
            ).also {
                finish()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        Log.d(TAG, "onActivityResult: $data")
//        if (null == data)
//            return
//
//        if (null == data.extras)
//            return
//
//        val extras: Bundle = data.extras!!
//        if (requestCode == BookingViewModel.ACCEPT_PAYMENT_REQUEST) {
//            when (resultCode) {
//
////                IntentConstants.USER_CANCELED -> {
////                    showToast(R.string.user_canceled)
////                }
////
////                IntentConstants.MISSING_ARGUMENT -> {
////                    showToast(R.string.missing_argument)
////                }
////
////                IntentConstants.TRANSACTION_ERROR -> {
////                    showToast(R.string.transaction_error)
////                }
////
////                IntentConstants.TRANSACTION_REJECTED -> {
////                    showToast(R.string.transaction_rejected)
////                }
////
////                IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE -> {
////                    showToast(R.string.transaction_rejected)
////                }
////
////                IntentConstants.TRANSACTION_SUCCESSFUL -> {
////                    showToast(R.string.successful_transaction)
////
////                    extras.getString(PayResponseKeys.SUCCESS)
////                    extras.getString(PayResponseKeys.ID)
////
////                    lifecycleScope.launch {
////                        bookingViewModel.submitBooking()
////                    }
////                }
////
////                IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE -> {
////                    showToast(R.string.successful_transaction)
////
////                    lifecycleScope.launch {
////                        bookingViewModel.submitBooking()
////                    }
////                }
////
////                IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED -> {
////                }
////
////                IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION -> {
////                    showToast(R.string.user_canceled)
////                }
////
////                IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE -> {
////                    showToast(R.string.user_canceled)
////                }
//
//            }
//        }
//    }


    override fun onError(error: PaymentSdkError) {
        Log.d(TAG, "onError: $error")
        handleFailure(exception = null, messageRes = R.string.payment_failed)
    }

    override fun onPaymentCancel() {
        handleFailure(exception = null, messageRes = R.string.payment_cancelled)
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        handleLoading(true)
        bookingViewModel.setTransactionRef(paymentSdkTransactionDetails.transactionReference)
        bookingViewModel.confirmReservation()
    }

    private fun payWithPayTabs(paymentSdkConfigurationDetails: PaymentSdkConfigurationDetails) {
        PaymentSdkActivity.startCardPayment(
            requireActivity(),
            paymentSdkConfigurationDetails,
            this
        )
    }
}