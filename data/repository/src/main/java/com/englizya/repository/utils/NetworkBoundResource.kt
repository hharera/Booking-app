package com.englizya.repository.utils

import android.util.Log
import com.englizya.repository.impl.OfferRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import okhttp3.internal.wait

const val TAG = "NetworkBoundResource"


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    var data = query().firstOrNull()

    if (data is List<*>) {
        if (data.isEmpty()) {
            data = null
        }
    }

    if (data == null) {
        emit(Resource.Loading(data))
        kotlin.runCatching {
            fetch()
        }.onFailure {
            emit(Resource.Error(it))
        }.onSuccess {
            saveFetchResult(it)
            query().collect {
                emit(Resource.Success(it))
            }
        }
    } else {
        if (shouldFetch(data)) {
            emit(Resource.Loading(data))
            kotlin.runCatching {
                fetch()
            }.onFailure {
                emit(Resource.Error(it))
            }.onSuccess {
                saveFetchResult(it)
                query().collect {
                    emit(Resource.Success(it))
                }
            }
        } else {
            emit(Resource.Success(data))
        }
    }
}