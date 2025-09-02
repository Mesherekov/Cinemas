package com.example.cinemas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun Movie(url: String){
    val isgetInfo = remember {
        mutableStateOf(false)
    }
    runBlocking {
        CoroutineScope(Dispatchers.IO).launch {
            MovieInfo.parsing(url)
            isgetInfo.value = true
        }
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            AsyncImage(
                model = if (isgetInfo.value) MovieInfo.movieData.image else "",
                contentDescription = "movie",
                placeholder = painterResource(R.drawable.camera),
                modifier = Modifier
                    .size(
                        width = 120.dp,
                        height = 180.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .padding(3.dp)
            )
            Text(if (isgetInfo.value) MovieInfo.movieData.rate else "")

        }
    }
}