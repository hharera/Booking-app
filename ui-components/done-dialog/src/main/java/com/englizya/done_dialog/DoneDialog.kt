package com.englizya.done_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.done_dialog.databinding.DialogDoneBinding

class DoneDialog : DialogFragment() {

    private lateinit var binding: DialogDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDoneBinding.inflate(layoutInflater)
        return binding.root
    }
}