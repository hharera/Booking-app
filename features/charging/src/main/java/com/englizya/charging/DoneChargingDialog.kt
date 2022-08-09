package com.englizya.charging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.charging.databinding.DialogDoneChargingBinding

class DoneChargingDialog(
    private val message: String,

    private val onOkButtonClicked: () -> Unit,

    ) : DialogFragment() {
    private lateinit var binding : DialogDoneChargingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogDoneChargingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMessage(message)

        setUpListeners()
    }
    private fun setMessage(message: String) {
        binding.dialogMessage.text = getString(R.string.done_charging_text, message)
    }
    private fun setUpListeners() {
        binding.okBtn.setOnClickListener {
            onOkButtonClicked()
        }
    }
}