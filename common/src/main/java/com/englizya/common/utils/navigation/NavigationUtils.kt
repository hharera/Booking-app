package com.englizya.common.utils.navigation

import android.net.Uri
import com.englizya.datastore.utils.Value
import com.englizya.datastore.utils.Value.NULL_STRING

object NavigationUtils {

    fun getUriNavigation(domain: String, destination: String, argument: String? = null): Uri {
        return "$domain://$destination/${argument ?: NULL_STRING}".let { Uri.parse(it) }
    }
}