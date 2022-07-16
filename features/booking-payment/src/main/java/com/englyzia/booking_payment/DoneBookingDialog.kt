package com.englyzia.booking_payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englyzia.booking_payment.databinding.DialogDoneBookingBinding

class DoneBookingDialog(
    private val onOkButtonClicked: () -> Unit,

    ): DialogFragment() {
    private lateinit var binding : DialogDoneBookingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogDoneBookingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.okBtn.setOnClickListener {
            onOkButtonClicked()
        }
    }
}