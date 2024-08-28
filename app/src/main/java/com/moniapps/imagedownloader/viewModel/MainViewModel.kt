package com.moniapps.imagedownloader.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moniapps.imagedownloader.data.remote.Photos
import com.moniapps.imagedownloader.data.repository.Repository
import com.moniapps.imagedownloader.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _imageData = MutableStateFlow<ApiResponse<Photos?>>(ApiResponse.Loading)
    var imageData: StateFlow<ApiResponse<Photos?>> = _imageData.asStateFlow()
    var searchQuery by mutableStateOf("")
    var pageNumbers by mutableIntStateOf(1)
    private val perPage by mutableIntStateOf(5)
    var searchButtonClicked by mutableStateOf(false)

    fun pageNumberChange(pageNo: Int) {
        pageNumbers = pageNo
    }

    fun searchTextChange(text: String) {
        searchQuery = text
    }
    fun circleProgressBarVisibility():Boolean
    = searchQuery.isNotEmpty() && searchButtonClicked
    fun callServer() {
        viewModelScope.launch {
            repository.searchImages(
                searchQuery = searchQuery,
                perPage = perPage,
                pageNumber = pageNumbers
            ).collect {
                _imageData.value = it
            }
        }


    }
}