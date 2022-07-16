package com.englyzia.reviewdriver

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.ImageUtils.convertBitmapToFile
import com.englizya.datastore.UserDataStore
import com.englizya.driver_review.R
import com.englizya.model.request.DriverReviewRequest
import com.englizya.repository.SupportRepository
import com.englyzia.reviewdriver.utils.Validity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DriverReviewViewModel constructor(
    private val supportRepository: SupportRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _driverCode: MutableLiveData<String> = MutableLiveData()
    val driverCode: LiveData<String> = _driverCode

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    private val _rating: MutableLiveData<Float> = MutableLiveData()
    val rating: LiveData<Float> = _rating

    private val _formValidity: MutableLiveData<DriverReviewForm> = MutableLiveData()
    val formValidity: LiveData<DriverReviewForm> = _formValidity

    private val _insertionCompleted = MutableLiveData(false)
    val insertionCompleted: LiveData<Boolean> = _insertionCompleted

    suspend fun insertDriverReview() {
        updateLoading(true)
        createDriverReviewRequest()
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
            .onSuccess {
                updateLoading(false)
                insertDriverReview(it)
            }

    }

    private suspend fun insertDriverReview(driverReviewRequest: DriverReviewRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            updateLoading(true)
            supportRepository
                .insertDriverReview(
                    driverReviewRequest = driverReviewRequest,
                    userDataStore.getToken()
                )
                .onSuccess {
                    updateLoading(false)
                    _insertionCompleted.postValue(true)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    private fun createDriverReviewRequest() = kotlin.runCatching {
        DriverReviewRequest(
            driverCode = Integer.parseInt(driverCode.value!!),
            review = rating.value!!.toInt(),
            complaintImage = image.value?.let { convertBitmapToFile(it) },
            reviewMessage = message.value
        )
    }

    private fun checkFormValidity() {
        if (driverCode.value.isNullOrBlank()) {
            _formValidity.postValue(DriverReviewForm(driverCodeError = R.string.empty_title_error))
        } else if (Validity.isDriverCodeValid(driverCode.value).not()) {
            _formValidity.postValue(DriverReviewForm(driverCodeError = R.string.invalid_driver_code))
        } else if (null == rating.value) {
            _formValidity.postValue(DriverReviewForm(reviewError = R.string.required_rating))
        } else if (Validity.isReviewValid(rating.value).not()) {
            _formValidity.postValue(DriverReviewForm(reviewError = R.string.required_rating))
        } else {
            _formValidity.postValue(DriverReviewForm(formIsValid = true))
        }
    }

    fun setImage(bitmap: Bitmap) {
        _image.postValue(bitmap)
        checkFormValidity()
    }

    fun setDriverReview(driverCode: String) {
        _driverCode.value = driverCode
        checkFormValidity()
    }

    fun setReviewMessage(desc: String) {
        _message.value = desc
        checkFormValidity()
    }

    fun setDriverRating(rating: Float) {
        _rating.value = rating
        checkFormValidity()
    }

}
