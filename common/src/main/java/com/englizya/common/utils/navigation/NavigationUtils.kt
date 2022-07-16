package com.englizya.common.utils.navigation

import android.net.Uri
import com.englizya.datastore.utils.Value.NULL_STRING

object NavigationUtils {

    fun getUriNavigation(
        domain: String,
        destination: String,
        containsArgs: Boolean = false,
        argument: String? = null
    ): Uri {
        return if (containsArgs) {
            "$domain://$destination/${argument}"
        } else {
            "$domain://$destination"
        }.let {
            Uri.parse(it)
        }
    }

    fun getUriNavigation(domain: String, destination: String, argument: String? = null): Uri {
        return "$domain://$destination/${argument ?: NULL_STRING}".let { Uri.parse(it) }
    }

    fun getUriNavigation(
        domain: String,
        destination: String,
        arguments: Array<String?> = emptyArray()
    ): Uri {
        val uri = "$domain://$destination"

        return if (arguments.isEmpty())
            uri.plus("/null").let { Uri.parse(it) }
        else {
            arguments.forEach { uri.plus("/$it") }
            return uri.let { Uri.parse(it) }
        }
    }
}