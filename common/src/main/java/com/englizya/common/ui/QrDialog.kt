package com.englizya.common.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.common.databinding.DialogQrBinding
import com.englizya.model.model.User
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.jasypt.util.text.TextEncryptor
import org.koin.android.ext.android.inject

class QrDialog(
    private val user: User?,
) : DialogFragment() {

    private lateinit var binding: DialogQrBinding
    private val textEncryptor : TextEncryptor by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogQrBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user?.let {
            BarcodeEncoder()
                .encodeBitmap(
                    user.uid,
                    BarcodeFormat.QR_CODE,
                    150,
                    150
                ).also {
                    binding.qr.setImageBitmap(it)
                }

            binding.walletOtp.text = (user.walletOtp)
        }

        isCancelable = true
    }
}