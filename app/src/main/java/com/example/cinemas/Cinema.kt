package com.example.cinemas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun Cinema(name: String,
           address: String,
           phone: String,
           url: String,
           cinemaurl: String
           ) {
    var movieData: MovieData = MovieData(emptyList(),
        emptyList(),
        emptyList())
    val isget = remember {
        mutableStateOf(false)
    }
    try{
    runBlocking {
        val waiter = CoroutineScope(Dispatchers.IO).async {
            parsingmovie(cinemaurl)
        }
        movieData = waiter.await()
        if (waiter.isCompleted)
            isget.value = true
    }
    }catch (ex: Exception){
        Log.e("Error", ex.toString())
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(
                color = Color.LightGray,
                width = 2.dp
            )) {
            AsyncImage(
                url,
                placeholder = painterResource(R.drawable.profile),
                contentDescription = "image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(3.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp)),
                error = painterResource(R.drawable.profile),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = name,
                fontSize = 40.sp,
                modifier = Modifier.padding(11.dp))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(color = Color.LightGray,
                width = 2.dp)) {
            Text("Адрес",
                fontSize = 23.sp,
                modifier = Modifier.padding(12.dp))
            Text(address,
                fontSize = 25.sp,
                modifier = Modifier.padding(12.dp))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(color = Color.LightGray,
                width = 2.dp)) {
            Text("Номер\nтелефона",
                fontSize = 22.sp,
                modifier = Modifier.padding(12.dp))
            Text(phone,
                fontSize = 21.sp,
                modifier = Modifier.padding(12.dp))
        }
    }
    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp).border(
        color = Color.Red,
        width = 5.dp
    )) {
//        LazyRow(modifier = Modifier.fillMaxWidth()) {
//            val movieSingle = mutableListOf<MovieSingle>()
//            for (i in 0 until movieData.urlImage.size){
//                movieSingle.add(MovieSingle(movieData.urlImage[i],
//                    movieData.numberofsessions[i],
//                    movieData.time[i]))
//            }
//            itemsIndexed(if(isget.value) movieSingle else emptyList()){ _, item ->
//                Movie(item)
//            }
//        }
    }
}

@Composable
fun Movie(
    movieSingle: MovieSingle
){
    Column {
        AsyncImage(
            model = movieSingle.url,
            contentDescription = "movie",
            placeholder = painterResource(R.drawable.camera)
        )
        Text(text = movieSingle.numofsessions,
            fontSize = 16.sp,
            color = Color.LightGray)
        LazyRow {
            itemsIndexed(movieSingle.time){ _, item->
                Box(modifier = Modifier.background(Color.Yellow)) {
                    Text(text = item, fontSize = 18.sp)
                }
            }
        }
    }
}