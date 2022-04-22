package com.englyzia.reviewdriver

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.ImageUtils.convertBitmapToFile
import com.englizya.driver_review.R
import com.englizya.model.request.ComplaintRequest
import com.englizya.repository.SupportRepository
import com.englyzia.reviewdriver.utils.Validity

class DriverReviewViewModel constructor(
    private val complaintRepository: SupportRepository,
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

    suspend fun insertComplaint() {
        updateLoading(true)
        createComplaintRequest()
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
            .onSuccess {
                updateLoading(false)
                insertComplaint(it)
            }

    }

    private suspend fun insertComplaint(complaintRequest: ComplaintRequest) {
        updateLoading(true)
        complaintRepository
            .insertComplaint(complaintRequest = complaintRequest)
            .onSuccess {
                updateLoading(false)
                _insertionCompleted.value = true
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun createComplaintRequest() = kotlin.runCatching {
        ComplaintRequest(
            complaintTitle = driverCode.value!!,
            complaintDesc = message.value!!,
            complaintImage = image.value?.let { convertBitmapToFile(it) }
        )
    }

    private fun checkFormValidity() {
        if (driverCode.value.isNullOrBlank()) {
            _formValidity.postValue(DriverReviewForm(reviewError = R.string.empty_title_error))
        } else if (driverCode.value?.let { Validity.isDriverCodeValid(it).not() } == true) {
            _formValidity.postValue(DriverReviewForm(driverCodeError = R.string.invalid_driver_code))
        }  else if (null == rating.value) {
            _formValidity.postValue(DriverReviewForm(reviewError = R.string.required_rating))
        }  else if (rating.value?.let { Validity.isReviewValid(it).not() } == true) {
            _formValidity.postValue(DriverReviewForm(driverCodeError = R.string.required_rating))
        } else {
            _formValidity.postValue(DriverReviewForm(formIsValid = true))
        }
    }

    fun setImage(bitmap: Bitmap) {
        _image.postValue(bitmap)
        checkFormValidity()
    }

    fun setTitle(title: String) {
        _driverCode.value = title
        checkFormValidity()
    }

    fun setDescription(desc: String) {
        _message.value = desc
        checkFormValidity()
    }

    fun setDriverRating(rating: Float) {
        _rating.value = rating
        checkFormValidity()
    }

}
