package com.moniapps.imagedownloader.utils

sealed class ApiResponse<out T> {
    data class Success<out R>(val data: R) : ApiResponse<R>()
    data object Loading : ApiResponse<Nothing>()
    data class Error(val message: String) : ApiResponse<Nothing>()

}