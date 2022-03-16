package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.KEY
import com.englizya.datastore.utils.SharedPreferences
import com.englizya.datastore.utils.Value.NULL_STRING

class UserDataStore(context: Context) {

    private val driverSharedPreferences =
        context.getSharedPreferences(
            SharedPreferences.USER_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    fun setToken(token: String) =
        driverSharedPreferences.edit().putString(KEY.TOKEN, token).apply()

    fun getToken(): String =
        driverSharedPreferences.getString(KEY.TOKEN, NULL_STRING)!!
}