package com.moniapps.imagedownloader.data.repository

import com.moniapps.imagedownloader.data.remote.api.APIService
import com.moniapps.imagedownloader.utils.result
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: APIService,
) {

    fun searchImages(
        searchQuery: String,
        pageNumber: Int,
        perPage: Int
    ) = result {
        apiService.searchPhotos(
            searchQuery = searchQuery,
            page = pageNumber,
            per_page = perPage
        )
    }


}