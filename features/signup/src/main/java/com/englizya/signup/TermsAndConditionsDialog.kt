package com.englizya.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.common.databinding.DialogDoneBinding
import com.englizya.signup.databinding.DialogTermsAndConditionsBinding

class TermsAndConditionsDialog(
    private val signupViewModel: SignupViewModel
) : DialogFragment() {

    private lateinit var binding: DialogTermsAndConditionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogTermsAndConditionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accept.setOnClickListener {
            signupViewModel.whenAccept()
            dismiss()
        }
    }
}