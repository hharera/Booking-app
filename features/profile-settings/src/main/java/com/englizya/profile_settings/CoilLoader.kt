package com.englizya.profile_settings

import android.content.Context
import coil.request.ImageRequest
import com.englizya.datastore.UserDataStore
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers


class CoilLoader(
    private val dataStore: UserDataStore,

    private val context: Context,
) {
    fun imageRequest(imageUrl: String?) =
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .dispatcher(Dispatchers.IO)
            .placeholder(R.drawable.ic_profile_user)
            .crossfade(true)
            .error(R.drawable.ic_profile_user)
            .fallback(R.drawable.ic_profile_user)
            .addHeader(HttpHeaders.Authorization, "Bearer ${dataStore.getToken()}")
            .build()
}