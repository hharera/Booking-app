package com.englizya.common.base

import android.content.Intent
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
import com.englizya.common.ui.ErrorDialog
import com.englizya.common.ui.LoadingDialog
import com.englizya.common.utils.network.ConnectionLiveData
import com.google.android.material.snackbar.Snackbar
import io.ktor.client.features.*
import io.ktor.http.*
import java.net.ConnectException
import java.util.*

open class BaseFragment : Fragment() {

    lateinit var connectionLiveData: ConnectionLiveData
    private val doneDialog: DoneDialog by lazy { DoneDialog() }

    val TAG = this::class.java.name
    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog()
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
//        setupLanguage()
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
        try {
            if (childFragmentManager.findFragmentByTag("LOADING")?.isAdded == false) {
                loadingDialog.show(childFragmentManager, "LOADING")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dismissLoading() {
        if (childFragmentManager.findFragmentByTag("LOADING")?.isAdded == true) {
            loadingDialog.dismiss()
        }
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
        checkExceptionType(exception)
    }

    fun handleFailure(throwable: Throwable?, messageRes: Int? = null) {
        throwable?.printStackTrace()
        checkExceptionType(Exception(throwable))
    }

    private fun checkExceptionType(throwable: Exception?) {
        when (throwable) {
            is ConnectException -> {
                activity?.window?.decorView?.let { showInternetSnackBar(it, false) }
            }

            is ClientRequestException -> {

                when (throwable.response.status) {
                    HttpStatusCode.Forbidden -> {
                        goLogin()
                    }
                    HttpStatusCode.BadRequest -> {
                        showErrorDialog(throwable.message.split("Text:")[1].dropWhile { it == '"' })
                    }
                }
            }
        }
    }

    private fun goLogin() {
        Class.forName("com.englizya.navigation.login.LoginActivity").let {
            Intent(context, it).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(this)
            }
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

    private fun showErrorDialog() {
        TODO("Not yet implemented")
    }

    private fun showErrorDialog(messageId: Int) {
        val dialog = ErrorDialog(getString(messageId))
        dialog.show(childFragmentManager, "errorDialog")
    }

    fun showErrorDialog(message: String) {
        val dialog = ErrorDialog(message)
        dialog.show(childFragmentManager, "errorDialog")
    }

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

