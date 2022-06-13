package com.englizya.user_tickets


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.englizya.user_tickets.databinding.DialogYesNoBinding

class YesNoDialog(
    onPositiveButtonClicked:(ticketId:String)->Unit,
    onNegativeButtonClicked:(ticketId:String)->Unit,
    ): DialogFragment() {
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
    fun setMessage( message:String?){

        binding.dialogMessage.setText(message)
    }

    fun setUpListeners(){
        binding.yesBtn.setOnClickListener {

        }

        binding.noBtn.setOnClickListener {
            findNavController().popBackStack()

        }

    }


}