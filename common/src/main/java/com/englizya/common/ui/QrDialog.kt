package com.englizya.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.common.databinding.DialogDoneBinding
import com.englizya.common.databinding.DialogQrBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrDialog(
    private val data: String?,
) : DialogFragment() {

    private lateinit var binding: DialogQrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogQrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data?.let {
            BarcodeEncoder().encodeBitmap(data, BarcodeFormat.QR_CODE, 150, 150).let {
                binding.qr.setImageBitmap(it)
            }
        }

        isCancelable = true
    }
}