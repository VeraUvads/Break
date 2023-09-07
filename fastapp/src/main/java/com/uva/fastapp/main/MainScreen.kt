package com.uva.fastapp.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val photos by mainViewModel.photos.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { mainViewModel.refresh() }
        ) {
            Text("refresh")
        }
        LazyVerticalGrid(
            GridCells.Adaptive(minSize = 200.dp)
        ) {
            items(photos) { photo ->
                PhotoItem(photo)
            }
        }
    }
}

@Composable
fun PhotoItem(photo: String) {
    AsyncImage(
        model = photo,
        contentDescription = "cat image",
        contentScale = ContentScale.Crop
    )
}
