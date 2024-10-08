package com.englizya.common.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.common.databinding.DialogErrorBinding

class ErrorDialog(private val message: String) : DialogFragment() {
    private lateinit var binding: DialogErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogErrorBinding.inflate(layoutInflater)
        return binding.root.also {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        setMessage(message)
    }

    private fun setMessage(message: String) {
        binding.message.text = message
    }
}