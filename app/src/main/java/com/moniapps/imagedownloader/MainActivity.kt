package com.moniapps.imagedownloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moniapps.imagedownloader.data.remote.PhotoX
import com.moniapps.imagedownloader.data.remote.Photos
import com.moniapps.imagedownloader.utils.ApiResponse
import com.moniapps.imagedownloader.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {    
            val imageDataState by viewModel.imageData.collectAsState()
            var showProcess by remember {
                mutableStateOf(true)
            }
            @Composable
            fun ShowProgressBar(
                showProcess: Boolean,
            ) {
                if (showProcess) {
                    CircularProgressIndicator(
                        strokeWidth = 6.dp,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            value = viewModel.searchQuery,
                            onValueChange = { viewModel.searchTextChange(it) },
                            label = { Text("Search") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                viewModel.searchButtonClicked = true
                                viewModel.callServer()
                            })
                        )

                        when (imageDataState) {
                            is ApiResponse.Error -> {
                                // Display an error message
                                Text("Error: ${(imageDataState as ApiResponse.Error).message}")
                            }

                            is ApiResponse.Loading -> {
                                if (viewModel.circleProgressBarVisibility())
                                    CircularProgressIndicator()
                            }

                            is ApiResponse.Success -> {
                                val photos =
                                    (imageDataState as ApiResponse.Success<Photos?>).data?.photos
                                Box(modifier = Modifier.fillMaxSize(.9f)){

                                    LazyColumn {
                                        items(photos ?: emptyList()) { item: PhotoX ->
                                            ShowProgressBar(showProcess = showProcess)
                                            AsyncImage(
                                                model = item.src.original,
                                                contentDescription = null,

                                                onSuccess = {
                                                    showProcess = false
                                                }
                                            )
                                        }
                                    }
                                }
                                Row (
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ){
                                    Button(
                                        onClick = {
                                            viewModel.pageNumbers = 1
                                            viewModel.callServer()
                                        },
                                        colors = if (viewModel.pageNumbers == 1) ButtonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Blue,
                                            disabledContentColor = Color.White
                                        ) else ButtonColors(
                                            containerColor = Color.White,
                                            contentColor = Color.Black,
                                            disabledContainerColor = Color.Blue,
                                            disabledContentColor = Color.White
                                        ),
                                        border = BorderStroke(2.dp, Color.Blue)
                                    ) {
                                        Text(text = "1")
                                    }
                                    Button(
                                        onClick = {
                                            viewModel.pageNumbers = 2
                                            viewModel.callServer()
                                        },
                                        colors = if (viewModel.pageNumbers == 2) ButtonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Blue,
                                            disabledContentColor = Color.White
                                        ) else ButtonColors(
                                            containerColor = Color.White,
                                            contentColor = Color.Black,
                                            disabledContainerColor = Color.Blue,
                                            disabledContentColor = Color.White
                                        ),
                                        border = BorderStroke(2.dp, Color.Blue)
                                    ) {
                                        Text(text = "2")
                                    }
                                    Button(
                                        onClick = {
                                            viewModel.pageNumbers = 3
                                            viewModel.callServer()
                                        },
                                        colors = if (viewModel.pageNumbers == 3) ButtonColors(
                                            containerColor = Color.Blue,
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Blue,
                                            disabledContentColor = Color.White
                                        ) else ButtonColors(
                                            containerColor = Color.White,
                                            contentColor = Color.Black,
                                            disabledContainerColor = Color.Blue,
                                            disabledContentColor = Color.White
                                        ),
                                        border = BorderStroke(2.dp, Color.Blue)
                                    ) {
                                        Text(text = "3")
                                    }
                                }
                            }
                        }

                    }
                }
            

        }
    }
}

