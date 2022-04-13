package com.englyzia.booking_payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.Seat
import com.englyzia.booking.BookingViewModel
import com.englyzia.booking.BookingViewModel.Companion.ACCEPT_PAYMENT_REQUEST
import com.englyzia.booking_payment.databinding.FragmentBookingPaymentBinding
import com.paymob.acceptsdk.*


class BookingPaymentFragment : BaseFragment() {

    private lateinit var binding: FragmentBookingPaymentBinding
    private val bookingViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtras()
    }

    private fun getExtras() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.pay.setOnClickListener {
            progressToPayment()
        }
    }

    private fun progressToPayment() {
        Intent(context, PayActivity::class.java).apply {
            putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)
            putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false)
            putExtra(PayActivityIntentKeys.THEME_COLOR, resources.getColor(R.color.blue_600))
            putExtra("ActionBar", true)
            putExtra("language", "ar")
            startActivityForResult(this, ACCEPT_PAYMENT_REQUEST)
        }
    }

    private fun setupObservers() {

    }

    private fun updateUI(data: List<Seat>) {
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (null == data)
            return

        if (null == data.extras)
            return

        val extras: Bundle = data.extras!!
        if (requestCode == BookingViewModel.ACCEPT_PAYMENT_REQUEST) {
            if (resultCode == IntentConstants.USER_CANCELED) {
                // User canceled and did no payment request was fired
                ToastMaker.displayShortToast(activity, "User canceled!!")
            } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                // You forgot to pass an important key-value pair in the intent's extras
                ToastMaker.displayShortToast(
                    activity,
                    "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                // An error occurred while handling an API's response
                ToastMaker.displayShortToast(
                    activity,
                    "Reason == " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                // User attempted to pay but their transaction was rejected

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(
                    activity,
                    extras.getString(PayResponseKeys.DATA_MESSAGE)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
                ToastMaker.displayShortToast(
                    activity,
                    extras.getString(IntentConstants.RAW_PAY_RESPONSE)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                // User finished their payment successfully

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(
                    activity,
                    extras.getString(PayResponseKeys.DATA_MESSAGE)
                )
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(activity, "TRANSACTION_SUCCESSFUL - Parsing Issue")

                // ToastMaker.displayShortToast(activity, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
                // User finished their payment successfully and card was saved.

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(
                    activity,
                    "Token == " + extras.getString(SaveCardResponseKeys.TOKEN)
                )
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
                ToastMaker.displayShortToast(activity, "User canceled 3-d scure verification!!")

                // Note that a payment process was attempted. You can extract the original returned values
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(activity, extras.getString(PayResponseKeys.PENDING))
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
                ToastMaker.displayShortToast(
                    activity,
                    "User canceled 3-d scure verification - Parsing Issue!!"
                )

                // Note that a payment process was attempted.
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(
                    activity,
                    extras.getString(IntentConstants.RAW_PAY_RESPONSE)
                )
            }
        }
    }
}