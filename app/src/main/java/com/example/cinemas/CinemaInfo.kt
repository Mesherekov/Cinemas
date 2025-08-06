package com.example.cinemas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.cinemas.data.MovieData
import com.example.cinemas.data.MovieSingle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@Composable
fun CinemaInfo(name: String,
               address: String,
               phone: String,
               url: String,
               cinemaurl: String
           ) {
    var movieData = MovieData(
        emptyList(),
        emptyList(),
        emptyList(),
        null
    )
    val isget = remember {
        mutableStateOf(false)
    }

    runBlocking {
        val waiter = CoroutineScope(Dispatchers.IO).async {
            parsingmovie(cinemaurl)
        }
        movieData = waiter.await()
        if (waiter.isCompleted) {
            isget.value = true
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    color = Color.LightGray,
                    width = 2.dp
                )
        ) {
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
            Text(
                text = name,
                fontSize = 40.sp,
                modifier = Modifier.padding(11.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    color = Color.LightGray,
                    width = 2.dp
                )
        ) {
            Text(
                "Адрес",
                fontSize = 23.sp,
                modifier = Modifier.padding(12.dp)
            )
            Text(
                address,
                fontSize = 25.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    color = Color.LightGray,
                    width = 2.dp
                )
        ) {
            Text(
                "Номер\nтелефона",
                fontSize = 22.sp,
                modifier = Modifier.padding(12.dp)
            )
            Text(
                phone,
                fontSize = 21.sp,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .border(
                color = Color(0xFFDC4700),
                width = 5.dp
            ).background(Color.White)
        ) {
            Column {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    val movieSingle = mutableListOf<MovieSingle>()
                    try {
                        for (i in 0 until movieData.urlImage.size) {
                            movieSingle.add(
                                MovieSingle(
                                    movieData.urlImage[i],
                                    movieData.numberofsessions[i],
                                    movieData.time[i]
                                )
                            )
                        }
                    } catch (ex: Exception) {
                        Log.e("Er", ex.toString())
                    }
                    itemsIndexed(if (isget.value) movieSingle else emptyList()) { _, item ->
                        Movie(item)
                    }
                }
                Button(onClick = {
                },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFE789),
                        contentColor = Color.White
                    )
                ) {
                    Text("Полное расписание и билеты",
                        fontSize = 18.sp,
                        color = Color.Black,)
                }
            }

        }
    }
}

@Composable
fun Movie(
    movieSingle: MovieSingle
){
    Column(modifier = Modifier.padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = movieSingle.url,
            contentDescription = "movie",
            placeholder = painterResource(R.drawable.camera),
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Text(text = movieSingle.numofsessions,
            fontSize = 16.sp,
            color = Color.Gray,
            )
        LazyRow(modifier = Modifier.size(
            width = 150.dp,
            height = 28.dp
        ),
            horizontalArrangement = Arrangement.Center) {
            itemsIndexed(movieSingle.time){ _, item->
                Box(modifier = Modifier
                    .background(Color(0xFFFFE789))
                    .padding(3.dp)) {
                    Text(text = item,
                        fontSize = 18.sp,
                        color = Color(0xFF000000))
                }
            }
        }
    }
}