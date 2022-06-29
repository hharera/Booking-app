package com.englizya.booking

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.booking.databinding.DialogRoundDiscountBinding

class RoundDiscountDialog : DialogFragment() {

    private lateinit var binding: DialogRoundDiscountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogRoundDiscountBinding.inflate(layoutInflater)
        isCancelable = true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }
}