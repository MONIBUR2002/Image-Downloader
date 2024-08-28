package com.moniapps.imagedownloader.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object Constants {
    const val API_KEY = "jIboeqkc1jcnUGDgVwgHjzIS7YC0AkIdqBVTEZj9YlWCnVUrvWwqykzV"
}
fun<T> result(call:suspend ()-> Response<T>):Flow<ApiResponse<T?>>
= flow {
    emit(ApiResponse.Loading)
    try {
        call().let {
            if (it.isSuccessful) {
                emit(ApiResponse.Success(it.body()))
            } else {
                call().errorBody()?.let {error->
                    emit(ApiResponse.Error(error.toString()))
                    error.close()
                }
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}