package com.englizya.user_tickets


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.englizya.user_tickets.databinding.DialogYesNoBinding

class YesNoDialog(
    private val onPositiveButtonClicked: (ticketId: String) -> Unit,
    private val onNegativeButtonClicked:  ()-> Unit,
    val ticketId: String?,
) : DialogFragment() {
    private lateinit var binding: DialogYesNoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogYesNoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()


    }

    private fun setUpListeners() {
        binding.yesBtn.setOnClickListener {
            onPositiveButtonClicked(ticketId!!)

        }

        binding.noBtn.setOnClickListener {
            onNegativeButtonClicked()
            dismiss()
        }

    }




}