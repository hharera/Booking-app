package com.englizya.complaint

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.ImageUtils.convertBitmapToFile
import com.englizya.complaint.util.Validity
import com.englizya.model.request.ComplaintRequest
import com.englizya.repository.SupportRepository

class ComplaintViewModel constructor(
    private val complaintRepository: SupportRepository,
) : BaseViewModel() {

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title

    private val _desc: MutableLiveData<String> = MutableLiveData()
    val desc: LiveData<String> = _desc

    private val _formValidity: MutableLiveData<ComplaintFormState> = MutableLiveData()
    val formValidity: LiveData<ComplaintFormState> = _formValidity

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
            complaintTitle = title.value!!,
            complaintDesc = desc.value!!,
            complaintImage = image.value?.let { convertBitmapToFile(it) }
        )
    }

    private fun checkFormValidity() {
        if (title.value.isNullOrEmpty()) {
            _formValidity.postValue(ComplaintFormState(titleError = R.string.empty_title_error))
        } else if (title.value?.let { Validity.isTitleValid(it).not() } == true) {
            _formValidity.postValue(ComplaintFormState(titleError = R.string.invalid_title_error))
        } else if (desc.value.isNullOrEmpty()) {
            _formValidity.postValue(ComplaintFormState(descriptionError = R.string.empty_desc_error))
        } else if (desc.value?.let { Validity.isDescValid(it).not() } == true) {
            _formValidity.postValue(ComplaintFormState(descriptionError = R.string.invalid_desc_error))
        } else {
            _formValidity.postValue(ComplaintFormState(formIsValid = true))
        }
    }

    fun setImage(bitmap: Bitmap) {
        _image.postValue(bitmap)
        checkFormValidity()
    }

    fun setTitle(title: String) {
        _title.value = title
        checkFormValidity()
    }

    fun setDescription(desc: String) {
        _desc.value = desc
        checkFormValidity()
    }

}
