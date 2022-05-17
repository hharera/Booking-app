package com.englizya.charging

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.charging.databinding.FragmentRechargingBinding
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkError
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RechargingFragment : BaseFragment(), CallbackPaymentInterface {

    private lateinit var binding: FragmentRechargingBinding
    private val chargingViewModel: RechargingViewModel by viewModel()

    companion object {
        private const val PAYMENT_REQ = 30005
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRechargingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        chargingViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        lifecycleScope.launch {
            chargingViewModel.formValidity.collectIndexed { index, value ->
                Log.d(TAG, "setupObservers: $value")
                binding.next.isEnabled = value?.formIsValid == true
            }
        }

        lifecycleScope.launch {
            chargingViewModel.paymentOrder.collectLatest {
                it?.let { paymentOrder -> chargingViewModel.rechargeBalance(paymentOrder) }
            }
        }

        lifecycleScope.launch {
            chargingViewModel.billingDetails.collectLatest { billingDetails ->
                billingDetails?.let { navigateToPayment(it) }
            }
        }

        lifecycleScope.launch {
            chargingViewModel.rechargingOperationState.collectLatest { state ->
                if (state == true) {
                    showDoneDialog()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.amount.afterTextChanged {
            chargingViewModel.setAmount(it)
        }

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.next.setOnClickListener {
            chargingViewModel.rechargeBalance()
        }
    }

    override fun onError(error: PaymentSdkError) {
        Log.d(TAG, "onError: $error")
        handleFailure(exception = null, messageRes = R.string.payment_failed)
    }

    override fun onPaymentCancel() {
        Log.d(TAG, "onPaymentCancel: ")
        findNavController().popBackStack()
        handleFailure(exception = null, messageRes = R.string.payment_cancelled)
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        Log.d(TAG, "onPaymentFinish: $paymentSdkTransactionDetails")
        handleLoading(true)
        val transactionReference = paymentSdkTransactionDetails.transactionReference
        chargingViewModel.setTransactionRef(transactionReference)
        chargingViewModel.rechargeBalance(transactionReference)
    }

    private fun navigateToPayment(paymentSdkConfigurationDetails: PaymentSdkConfigurationDetails) {
        PaymentSdkActivity.startCardPayment(
            requireActivity(),
            paymentSdkConfigurationDetails,
            this
        )
    }
}