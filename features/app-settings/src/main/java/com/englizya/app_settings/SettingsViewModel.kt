package com.englizya.app_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.datastore.utils.Language

class SettingsViewModel(
    private val dataStore: UserDataStore,
) : BaseViewModel() {

    private val _appLanguage = MutableLiveData<String>()
    val appLanguage : LiveData<String> = _appLanguage

    private val _selectedLanguage = MutableLiveData<Language>()
    val selectedLanguage : LiveData<Language> = _selectedLanguage

    init {
        getAppLanguage()
    }

    fun setSelectedLanguage(language: Language) {
        _selectedLanguage.value = language
    }

    fun saveSelectedLanguage() {
        selectedLanguage.value?.let { dataStore.setLanguage(it) }
    }

    private fun getAppLanguage() {
        dataStore.getLanguage().let {
            _appLanguage.value = it
            _selectedLanguage.value = when(it) {
                Language.English.key -> Language.English
                else  -> Language.Arabic
            }
        }
    }
}