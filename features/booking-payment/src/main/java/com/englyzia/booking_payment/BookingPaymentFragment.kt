package com.englyzia.booking_payment

import android.content.Intent
import android.net.Uri
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
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.response.InvoicePaymentResponse
import com.englyzia.booking.BookingViewModel
import com.englyzia.booking.utils.PaymentMethod
import com.englyzia.booking_payment.databinding.FragmentBookingPaymentBinding
import com.harera.user_tickets.UserTicketsActivity
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkError
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookingPaymentFragment : BaseFragment(), CallbackPaymentInterface {

    private lateinit var binding: FragmentBookingPaymentBinding
    private val bookingPaymentViewModel: BookingPaymentViewModel by sharedViewModel()
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
        binding.charge.setOnClickListener {
            navigateToRecharging()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()

//            bookingViewModel.clearReservationOrder().let {
//                findNavController().popBackStack()
//                bookingViewModel.selectedSeats.observe(viewLifecycleOwner) {
//                    it?.forEach { seat ->
//                        bookingViewModel.setSelectedSeat(seat)
//                        Log.d("BookingPaymentFragment", "selected seat: $seat")
//                    }
//
//
//                }
//            }
        }
//        requireActivity().onBackPressedDispatcher.addCallback {
//            activity?.onBackPressed()
//        }

//        binding.back.setOnClickListener {
//            bookingViewModel.clearReservationOrder().let {
//                activity?.onBackPressed()
//            }
//            activity?.onBackPressed()
//
//        }

        binding.pay.setOnClickListener {
            bookingViewModel.whenPayButtonClicked()
        }

        binding.cardMethodCL.setOnClickListener {
            bookingViewModel.setSelectedPaymentMethod(PaymentMethod.Card)
            updateTotal(0.0)
            updateSelectedMethodUI(it.id)
        }

        binding.englizyaWalletCL.setOnClickListener {
            bookingViewModel.setSelectedPaymentMethod(PaymentMethod.EnglizyaWallet)
            updateTotal(0.2)
            updateSelectedMethodUI(it.id)
        }

        binding.fawryWalletCL.setOnClickListener {
            bookingViewModel.setSelectedPaymentMethod(PaymentMethod.FawryPayment)
            updateTotal(0.0)
            updateSelectedMethodUI(it.id)
        }

        binding.meezaCL.setOnClickListener {
            bookingViewModel.setSelectedPaymentMethod(PaymentMethod.MeezaPayment)
            updateTotal(0.0)
            updateSelectedMethodUI(it.id)
        }

        binding.pay.setOnClickListener {
            bookingViewModel.whenPayButtonClicked()
        }
    }

    private fun updateTotal(discount: Double) {

        binding.discountTV.text = (bookingViewModel.total.value?.times(discount)).toString()
        binding.totalTV.text = bookingViewModel.total.value?.times(1 - discount).toString()
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

    private fun setupObservers() {
//        activity?.onBackPressedDispatcher?.addCallback {
//            findNavController().popBackStack()
//        }

        lifecycleScope.launch {
            bookingViewModel.billingDetails.collect {
                it?.let {
                    payWithPayTabs(it)
                }
            }
        }

        lifecycleScope.launch {
            bookingPaymentViewModel.userBalance.collectLatest {
                binding.balance.text = it.toString()

            }
        }

        bookingViewModel.total.observe(viewLifecycleOwner) {
            binding.subtotalTV.text = it.toString()
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


        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }

        bookingViewModel.invoicePaymentResponse.observe(viewLifecycleOwner) {
            redirect(it)
        }
    }

    private fun redirect(invoicePaymentResponse: InvoicePaymentResponse) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(invoicePaymentResponse.invoiceLink)
        })
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

    private fun navigateToRecharging() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.RECHARGING,
                Destination.PAYMENT
            )
        )
    }

    override fun onResume() {
        super.onResume()
        bookingPaymentViewModel.getUserBalance()
    }
}