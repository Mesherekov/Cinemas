package com.example.cinemas

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun Movie(url: String){
    val movieInfo = MovieInfo(url)
    runBlocking {
        CoroutineScope(Dispatchers.IO).launch {
            movieInfo.parsing()
        }
    }
}