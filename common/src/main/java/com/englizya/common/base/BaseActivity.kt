package com.englizya.common.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.englizya.common.R
import com.englizya.common.ui.LoadingDialog
import com.englizya.common.utils.network.ConnectionLiveData
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    lateinit var connectionLiveData: ConnectionLiveData

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(messageRes: Int) {
        Toast.makeText(this, getText(messageRes), Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
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

    fun <T : AppCompatActivity> gotToActivity(activityClass: Class<T>) {
        startActivity(Intent(this, activityClass))
            .also { finish() }
    }

    fun changeStatusBarColor(colorRes : Int) {
        window?.statusBarColor = getColor(colorRes)
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
        val sbView: View = snackBar.view
        val sbText: TextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text)
        sbText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_wifi, 0, 0, 0);
        sbText.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen._4sdp)
    }
}
