package com.englizya.common.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.englizya.common.R
import com.englizya.common.ui.DoneDialog
import com.englizya.common.ui.LoadingDialog
import com.englizya.common.utils.network.ConnectionLiveData
import com.google.android.material.snackbar.Snackbar
import java.util.*

open class BaseFragment : Fragment() {

    lateinit var connectionLiveData: ConnectionLiveData
    private val doneDialog: DoneDialog by lazy { DoneDialog() }

    val TAG = this::class.java.name
    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupLanguage()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setupLanguage() {
        val locale = Locale.getDefault()
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun handleLoading(state: Boolean) {
        if (state) {
            showLoading()
        } else {
            dismissLoading()
        }
    }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(messageRes: Int) {
        Toast.makeText(context, getText(messageRes), Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun handleFailure(exception: Exception?, messageRes: Int? = null) {
        exception?.printStackTrace()
        messageRes?.let { res ->
            showToast(res)
        }
    }

    fun handleFailure(throwable: Throwable?, messageRes: Int? = null) {
        throwable?.printStackTrace()
        messageRes?.let { res ->
            showToast(res)
        }
    }

    fun changeStatusBarColor(colorRes: Int) {
        activity?.window?.statusBarColor = resources.getColor(colorRes)
    }

    fun showDoneDialog() {
        doneDialog.show(childFragmentManager, TAG)
    }

    fun checkOperationState(state: Boolean) {
        if (state) {
            dismissDoneDialog()
        } else {
            showErrorDialog()
        }
    }

    private fun showErrorDialog() {}

    fun dismissDoneDialog() {
        doneDialog.dismiss()
    }

    fun showInternetSnackBar(view: View, internetState: Boolean) {
        if (internetState.not())
            Snackbar.make(
                view,
                R.string.no_internet,
                Snackbar.LENGTH_SHORT
            ).apply {
                setIconToSnackBar(this)
                show()
            }
    }

    private fun setIconToSnackBar(snackBar: Snackbar) {
        val sbView: View = snackBar.getView()
        val sbText: TextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text)
        sbText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_wifi, 0, 0, 0);
        sbText.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen._10sdp));

    }
}

