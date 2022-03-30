package com.englizya.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.BookingOffice
import com.englizya.repository.BookingOfficeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val officeRepository: BookingOfficeRepository,
) : BaseViewModel() {

    private var _formValidity = MutableLiveData<BookingFormState>()
    val formValidity: LiveData<BookingFormState> = _formValidity

    private var _source = MutableLiveData<String>()
    val source: LiveData<String> = _source

    private var _destination = MutableLiveData<String>()
    val destination: LiveData<String> = _destination

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private var _bookingOffices = MutableLiveData<List<BookingOffice>>()
    val bookingOffices: LiveData<List<BookingOffice>> = _bookingOffices

    suspend fun getBookingOffices() {
        updateLoading(true)
        officeRepository
            .getAllBookingOffices()
            .onSuccess {
                updateLoading(false)
                _bookingOffices.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun checkFormValidity() {
        if (_source.value.isNullOrBlank()) {
            _formValidity.postValue(BookingFormState(sourceError = R.string.source_is_required))
        } else if (_destination.value.isNullOrBlank()) {
            _formValidity.postValue(BookingFormState(sourceError = R.string.destincation_is_required))
        } else if (_date.value.isNullOrBlank()) {
            _formValidity.postValue(BookingFormState(sourceError = R.string.date_is_required))
        } else {
            _formValidity.postValue(BookingFormState(formIsValid = true))
        }
    }


    fun setDestination(destination: String) {
        _destination.value = destination
        checkFormValidity()
    }

    fun setSource(source: String) {
        _source.value = source
        checkFormValidity()
    }

    fun setDate(date: String) {
        _date.value = date
        checkFormValidity()
    }
}
