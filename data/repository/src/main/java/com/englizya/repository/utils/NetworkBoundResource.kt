package com.englizya.repository.utils

import kotlinx.coroutines.flow.*


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
            emit(Resource.Success(query().first()))
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
                emit(Resource.Success(query().first()))
            }
        } else {
            emit(Resource.Success(data))
        }
    }
}