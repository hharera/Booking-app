package com.englizya.common.base

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.englizya.common.utils.exception.CustomException
import com.englizya.common.utils.exception.CustomException.AuthorizationException
import io.ktor.client.features.*
import io.ktor.http.*

open class BaseViewModel : ViewModel() {

    val TAG = this::class.java.name

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _exception: MutableLiveData<CustomException> = MutableLiveData()
    val exception: LiveData<CustomException> = _exception

    private val _error: MutableLiveData<Exception?> = MutableLiveData()
    val error: LiveData<Exception?> = _error

    private val _connectivity: MutableLiveData<Boolean> = MutableLiveData(false)
    val connectivity: LiveData<Boolean> = _connectivity

    fun handleException(exception: Exception?) {
        exception?.let { checkExceptionType(exception) }
        exception?.printStackTrace()
    }

    fun handleException(exception: Int) {
        Resources.getSystem().getString(exception)
    }

    fun updateLoading(state: Boolean) {
        _loading.postValue(state)
    }

    private fun checkExceptionType(exception: Any) {
        when (exception) {
            is ClientRequestException -> {
                handleHttpException(exception)
            }
        }
    }

    private fun handleHttpException(exception: ClientRequestException) {
        when (exception.response.status) {
            HttpStatusCode.Unauthorized -> {
                _exception.postValue(AuthorizationException)
            }
        }
    }

    fun handleException(exception: Throwable?) {
        exception?.let {
            it.printStackTrace()
            checkExceptionType(exception)
        }
    }

    fun updateConnectivity(connectivity: Boolean) {
        _connectivity.postValue(connectivity)
    }
}