package com.englizya.add_payment

import android.os.Bundle
import com.englizya.add_payment.databinding.ActivitytAddPaymentBinding
import com.englizya.common.base.BaseActivity
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.model.model.PaymentCard
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPaymentCardActivity : BaseActivity() {

    private val addPaymentCardViewModel: AddPaymentCardViewModel by viewModel()
    private lateinit var bind: ActivitytAddPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitytAddPaymentBinding.inflate(layoutInflater)
        setContentView(bind.root)

        changeStatusBarColor(R.color.blue_600)
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        bind.cvv.setText(addPaymentCardViewModel.cardCvv.value)
        bind.expirationDate.setText(addPaymentCardViewModel.cardExpiration.value)
        bind.cardOwner.setText(addPaymentCardViewModel.cardOwner.value)
        bind.cardNumber.setText(addPaymentCardViewModel.cardNumber.value)
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        addPaymentCardViewModel.loading.observe(this) {
            handleLoading(it)
        }

        addPaymentCardViewModel.error.observe(this) {
            handleFailure(exception = it)
        }

        addPaymentCardViewModel.formValidity.observe(this) {
            bind.add.isEnabled = it.isValid

            if (it.cardOwnerError != null) {
                bind.textInputLayoutCardOwner.error = getString(it.cardOwnerError!!)
            } else {
                bind.textInputLayoutCardOwner.error = null
            }

            if (it.cardNumberError != null) {
                bind.textInputLayoutCardNumber.error = getString(it.cardNumberError!!)
            } else {
                bind.textInputLayoutCardNumber.error = null
            }

            if (it.cardCvvError != null) {
                bind.textInputLayoutCVV.error = getString(it.cardCvvError!!)
            } else {
                bind.textInputLayoutCVV.error = null
            }

            if (it.cardExpirationError != null) {
                bind.textInputLayoutExpirationDate.error = getString(it.cardExpirationError!!)
            } else {
                bind.textInputLayoutExpirationDate.error = null
            }
        }
    }

    private fun setupListeners() {
        bind.cardOwner.afterTextChanged { phoneNumber ->
            addPaymentCardViewModel.setCardOwner(phoneNumber)
        }


        bind.add.setOnClickListener {
            back()
            bind.add.isEnabled = false
        }
    }

    private fun back() {
        intent.putExtra(
            Arguments.PAYMENT_CARD, PaymentCard(
                addPaymentCardViewModel.cardNumber.value!!,
                addPaymentCardViewModel.cardCvv.value!!,
                addPaymentCardViewModel.cardOwner.value!!,
                addPaymentCardViewModel.cardExpiration.value!!,
            )
        )
        finish()
    }
}