package com.englizya.add_payment

import android.os.Bundle
import androidx.activity.viewModels
import com.englizya.add_payment.databinding.ActivitytAddPaymentBinding
import com.englizya.common.base.BaseActivity
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.model.model.PaymentCard

class AddPaymentCardActivity : BaseActivity() {

    private val addPaymentCardViewModel: AddPaymentCardViewModel by viewModels()
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
                bind.cardOwner.error = getString(it.cardOwnerError!!)
            }

            if (it.cardNumberError != null) {
                bind.cardNumber.error = getString(it.cardNumberError!!)
            }

            if (it.cardCvvError != null) {
                bind.cvv.error = getString(it.cardCvvError!!)
            }

            if (it.cardExpirationError != null) {
                bind.expirationDate.error = getString(it.cardExpirationError!!)
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