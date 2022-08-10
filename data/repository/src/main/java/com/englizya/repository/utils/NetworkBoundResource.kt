package com.englizya.repository.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import okhttp3.internal.wait


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    val data = query().firstOrNull()

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
        if(shouldFetch(data)) {
            emit(Resource.Loading(data))
            kotlin.runCatching {
                fetch()
            }.onFailure {
                emit(Resource.Error(it))
            }.onSuccess {
                saveFetchResult(it)
                query().collect {
                    emit(Resource.Success(it))
                }            }
        } else {
            emit(Resource.Success(data))
        }
    }
}