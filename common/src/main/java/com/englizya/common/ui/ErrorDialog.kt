package com.englizya.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.common.databinding.DialogDoneBinding

class ErrorDialog : DialogFragment() {

    private lateinit var binding: DialogDoneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogDoneBinding.inflate(layoutInflater)
        return binding.root
    }
}