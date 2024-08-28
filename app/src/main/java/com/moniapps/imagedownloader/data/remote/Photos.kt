package com.moniapps.imagedownloader.data.remote

data class Photos(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoX>,
    val total_results: Int
)