package com.englyzia.booking_payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englyzia.booking_payment.databinding.DialogPaymentConfirmationBinding

class PaymentConfirmationDialog(
    private val message: String,
    private val onPositiveButtonClicked: () -> Unit,
    private val onNegativeButtonClicked: () -> Unit,
) : DialogFragment() {
    private lateinit var binding: DialogPaymentConfirmationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogPaymentConfirmationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMessage(message)
        setUpListeners()


    }

    private fun setMessage(message: String) {
        binding.dialogMessage.text = getString(R.string.dialog_message, message)
    }

    private fun setUpListeners() {
        binding.okBtn.setOnClickListener {
            onPositiveButtonClicked()
        }
        binding.cancelBtn.setOnClickListener {
            onNegativeButtonClicked()
        }

    }


}