package com.englizya.charging

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.charging.databinding.FragmentRechargingBinding
import com.englizya.charging.utils.RechargeMethod
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.model.response.InvoicePaymentResponse
import com.englyzia.paytabs.utils.PaymentMethod
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkError
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RechargingFragment : BaseFragment(), CallbackPaymentInterface {

    private lateinit var binding: FragmentRechargingBinding
    private val chargingViewModel: RechargingViewModel by viewModel()
    private var doneChargingDialog: DoneChargingDialog? = null
    private var rechargeInfoDialog: RechargeInformationDialog? = null


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


        chargingViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.next.isEnabled = it.formIsValid
            if (it.amountRes != null) {
                if (it.amountRes == R.string.charging_amount_empty) {
                    binding.textInputLayoutCharge.error = getString(R.string.charging_amount_empty)

                } else if (it.amountRes == R.string.invalid_amount_empty) {
                    binding.textInputLayoutCharge.error = getString(R.string.invalid_amount_empty)

                } else if (it.amountRes == R.string.invalid_amount) {
                    binding.textInputLayoutCharge.error = getString(R.string.invalid_amount)

                }

            } else {
                binding.textInputLayoutCharge.error = null

            }

        }

        lifecycleScope.launch(Dispatchers.IO) {
            chargingViewModel.paymentOrder.collectLatest {
                it?.let { paymentOrder -> chargingViewModel.rechargeBalance(paymentOrder) }
            }
        }

        chargingViewModel.paymentInvoiceOrder.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                chargingViewModel.requestRechargeByInvoice(it)
                Log.d("paymentOrder ", it.orderId.toString())

                Log.d("requestRechargeByInvoice", "requestRechargeByInvoice")
            }

        }

        lifecycleScope.launch(Dispatchers.IO) {
            chargingViewModel.billingDetails.collectLatest { billingDetails ->
                billingDetails?.let { navigateToPayment(it) }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            chargingViewModel.rechargingOperationState.collectLatest { state ->
                state.let {
                    if (it == true) {
                        doneChargingDialog = DoneChargingDialog(
                            message = chargingViewModel.amount.value.toString(),
                            onOkButtonClicked = {
                                doneChargingDialog!!.dismiss()
                            }
                        )
                        doneChargingDialog!!.show(childFragmentManager, "DoneChargingDialog")
                    } else {
//                        showErrorDialog(getString(R.string.error_text))
                    }
                }
            }


        }
        chargingViewModel.invoicePaymentResponse.observe(viewLifecycleOwner) {
            Log.d("Invoice", it.invoiceLink.toString())
            redirect(it)

        }
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }
    }

    private fun redirect(invoicePaymentResponse: InvoicePaymentResponse) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(invoicePaymentResponse.invoiceLink)
        })
    }

    private fun updateSelectedMethodUI(id: Int) {
        binding.methodsGL.forEach {
            if (it.id == id) {
                it.setBackgroundResource(R.drawable.background_button_recharge_method_selected)

            } else {
                it.setBackgroundResource(R.drawable.background_button_recharge_method)
            }
        }
    }

    private fun showDialog() {
        rechargeInfoDialog = RechargeInformationDialog(
            onOkButtonClicked = { onOkButtonClicked() }
        )
        rechargeInfoDialog!!.show(childFragmentManager, "paymentDialog")

    }

    private fun onOkButtonClicked() {
        rechargeInfoDialog?.dismiss()
    }

    private fun setupListeners() {
        binding.cardMethodCL.setOnClickListener {
            chargingViewModel.setSelectedPaymentMethod(RechargeMethod.Card)

            updateSelectedMethodUI(it.id)
        }
        binding.fawryWalletCL.setOnClickListener {
            chargingViewModel.setSelectedPaymentMethod(RechargeMethod.FawryPayment)

            updateSelectedMethodUI(it.id)
        }
        binding.vodafoneCL.setOnClickListener {
            chargingViewModel.setSelectedPaymentMethod(RechargeMethod.VodafonePayment)
            showDialog()
            updateSelectedMethodUI(it.id)
        }
        binding.etisalatCL.setOnClickListener {
            chargingViewModel.setSelectedPaymentMethod(RechargeMethod.EtisalatPayment)
            showDialog()
            updateSelectedMethodUI(it.id)
        }
        binding.orangeCL.setOnClickListener {
            chargingViewModel.setSelectedPaymentMethod(RechargeMethod.OrangePayment)
            showDialog()
            updateSelectedMethodUI(it.id)
        }
        binding.meezaCL.setOnClickListener {
            chargingViewModel.setSelectedPaymentMethod(RechargeMethod.MeezaPayment)

            updateSelectedMethodUI(it.id)
        }



        binding.amount.afterTextChanged {
            chargingViewModel.setAmount(it)
        }

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.next.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                chargingViewModel.whenRechargeButtonClicked()

            }
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
//        handleLoading(true)
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